package me.itangqi.buildingblocks.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.api.ZhihuApi;
import me.itangqi.buildingblocks.domain.application.App;
import me.itangqi.buildingblocks.domain.db.SQLiteHelper;
import me.itangqi.buildingblocks.domain.utils.Constants;
import me.itangqi.buildingblocks.domain.utils.PrefUtils;
import me.itangqi.buildingblocks.domain.utils.ThemeUtils;
import me.itangqi.buildingblocks.domain.utils.ToastUtils;
import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.model.entity.DailyResult;
import me.itangqi.buildingblocks.presenters.WebActivityPresenter;

/**
 * Created by Troy on 2015/9/21.
 * <br/>
 * 数据交换中枢，负责数据的存储和获取
 * <br/>
 * <b>通过API获取请求地址的时候，传入的日期时间要比当前时间大一天，而返回的数据里面的date属性又是当前时间，这样一来造成了，读取数据库数据与储存数据混乱</b>
 */
public class DailyModel implements IDaily {

    public static final String TAG = "DailyModel";

    private IHttpCallBack mIHttpCallBack;
    private IGsonCallBack mIGsonCallBack;
    private SQLiteHelper mSQLiteHelper;
    private static DailyModel mDailyModel;

    // 用来判断是否已从网上获取了数据，从而避免重复获取
    private boolean hasReadFromNet = false;

    //用来获取Daily集合
    AsyncHttpResponseHandler mAsyncHttpResponseHandler = new BaseJsonHttpResponseHandler<DailyResult>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyResult response) {
            if (response != null && response.stories.size() != 0) {
                hasReadFromNet = true;
                saveDailyStoriesDB(response);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyResult errorResponse) {
            Log.d(TAG, "解析错误,状态码:" + statusCode);
            if (statusCode == 0){
//                ToastUtils.showShort(R.string.network_error_toast);
            } else {
                ToastUtils.showShort(R.string.others_error_toast);
            }
            throwable.printStackTrace();
        }

        @Override
        protected DailyResult parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyResult.class);
        }
    };

    // 用来获取DailyGson的数据
    AsyncHttpResponseHandler mGsonNewsResponseHandler = new BaseJsonHttpResponseHandler<DailyGson>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyGson response) {
            mIGsonCallBack.onGsonItemFinish(response);
            saveDailyGsonDB(response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyGson errorResponse) {
            Log.d(TAG, "解析错误,状态码:" + statusCode);
            if (statusCode == 0){
                ToastUtils.showShort(R.string.network_error_toast);
            } else {
                ToastUtils.showShort(R.string.others_error_toast);
            }
            throwable.printStackTrace();
        }

        @Override
        protected DailyGson parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyGson.class);
        }
    };

    /**
     * 创建一个Model对象
     *
     * @param iHttpCallBack 用来实现实现数据(非GSON数据)完成后的回调接口，主要用于RecyclerView显示
     * @return 返回Model对象
     */
    public static DailyModel newInstance(IHttpCallBack iHttpCallBack) {
        if (mDailyModel == null) {
            return new DailyModel(iHttpCallBack);
        } else {
            return mDailyModel;
        }
    }

    /**
     * 创建一个Model对象
     *
     * @param iGsonCallBack 用来实现实现GSON数据获取完成后的回调接口，主要用于RecyclerView显示
     * @return 返回Model对象
     */
    public static DailyModel newInstance(IGsonCallBack iGsonCallBack) {
        if (mDailyModel == null) {
            return new DailyModel(iGsonCallBack);
        } else {
            return mDailyModel;
        }
    }

    public static DailyModel newInstance() {
        if (mDailyModel == null) {
            return new DailyModel();
        } else {
            return mDailyModel;
        }
    }

    private DailyModel() {
        mSQLiteHelper = new SQLiteHelper();
    }

    private DailyModel(IHttpCallBack IHttpCallBack) {
        this();
        this.mIHttpCallBack = IHttpCallBack;
    }

    private DailyModel(IGsonCallBack IGsonCallBack) {
        this();
        this.mIGsonCallBack = IGsonCallBack;
    }

    /**
     * 只暴露了此方法给Presenter使用，根据Pref来判断数据获取的方式以及顺序
     * 获取的数据供RecyclerView来显示
     *
     * @param date 当前的时间 <b>+1天</b>
     */
    public void getDailyResult(int date) {
        hasReadFromNet = false;
        if (PrefUtils.isEnableCache()) {
            getFromCache(date);
        } else {
            getFromNet(date);
        }
    }

    private void getFromNet(int date) {
        if (PrefUtils.isEnableCache()) {
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(Constants.simpleDateFormat.parse(date + ""));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date = Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime()));
        }
        String url = ZhihuApi.getDailyNews(date);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, mAsyncHttpResponseHandler);
    }

    private void getFromCache(int date) {
        getDailyStoriesDB(date);
    }

    /**
     * 获取在gson模式下的New数据
     *
     * @param id 获取gson数据的唯一id
     */
    @Override
    public void getGsonNews(int id) {
        if (getDailyGsonDB(id) == null) {
            String url = ZhihuApi.getGsonNewsContent(id);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, mGsonNewsResponseHandler);
        }
    }

    private void saveDailyGsonDB(DailyGson daily) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", daily.id);
        values.put("title", daily.title);
        values.put("type", daily.type);
        values.put("image_source", daily.image_source);
        values.put("image", daily.image);
        values.put("share_url", daily.share_url);
        values.put("ga_prefix", daily.ga_prefix);
        values.put("body", daily.body);
        database.insertWithOnConflict("daily", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        database.close();
    }

    private DailyGson getDailyGsonDB(int id) {
        SQLiteDatabase database = mSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM daily where id = ?", new String[]{id + ""});
        DailyGson dailyGson = new DailyGson();
        if (cursor.moveToFirst()) {
            dailyGson.id = cursor.getInt(cursor.getColumnIndex("id"));
            dailyGson.title = cursor.getString(cursor.getColumnIndex("title"));
            dailyGson.type = cursor.getInt(cursor.getColumnIndex("type"));
            dailyGson.image_source = cursor.getString(cursor.getColumnIndex("image_source"));
            dailyGson.image = cursor.getString(cursor.getColumnIndex("image"));
            dailyGson.share_url = cursor.getString(cursor.getColumnIndex("share_url"));
            dailyGson.ga_prefix = cursor.getInt(cursor.getColumnIndex("ga_prefix"));
            dailyGson.body = cursor.getString(cursor.getColumnIndex("body"));
            cursor.close();
            mIGsonCallBack.onGsonItemFinish(dailyGson);
            database.close();
            return dailyGson;
        }
        database.close();
        return null;
    }

    private void saveDailyStoriesDB(DailyResult dailyResult) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Daily story : dailyResult.stories) {
            values.put("date", dailyResult.date);
            values.put("id", story.id);
            values.put("title", story.title);
            values.put("image", story.images.get(0));
            values.put("type", story.type);
            values.put("ga_prefix", story.ga_prefix);
            database.insertWithOnConflict("dailyresult", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }
        database.close();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Constants.simpleDateFormat.parse(dailyResult.date + ""));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getDailyStoriesDB(Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime())));
    }

    private void getDailyStoriesDB(int date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Constants.simpleDateFormat.parse(date + ""));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        List<Daily> dailyList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM dailyresult WHERE date = ?", new String[]{date + ""});
        while (cursor.moveToNext()) {
            Daily daily = new Daily();
            daily.id = cursor.getInt(cursor.getColumnIndex("id"));
            daily.title = cursor.getString(cursor.getColumnIndex("title"));
            daily.image = cursor.getString(cursor.getColumnIndex("image"));
            daily.type = cursor.getInt(cursor.getColumnIndex("type"));
            daily.ga_prefix = cursor.getInt(cursor.getColumnIndex("ga_prefix"));
            dailyList.add(daily);
        }
        cursor.close();
        database.close();
        mIHttpCallBack.onFinish(dailyList);
        if (!hasReadFromNet) {
            hasReadFromNet = true;
            getFromNet(date);
        }
    }

    /**
     * 使用Jsoup来对数据库里面的DaliyGson中的body字段进行解析
     *
     * @param dailyGson 待解析的gson对象
     * @return 返回一个包含额外信息和正文的HashMap
     */
    public Map<String, LinkedHashMap<String, String>> parseBody(DailyGson dailyGson) {
        long before = System.currentTimeMillis();
        String xml = dailyGson.body;
        Map<String, LinkedHashMap<String, String>> soup = new HashMap<String, LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> extra = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> article = new LinkedHashMap<String, String>();
        Document document = Jsoup.parse(xml, "", new Parser(new XmlTreeBuilder()));
        Elements all = document.getAllElements();
        for (Element content : all) {
            if (content.hasClass("avatar")) {
                String src = content.attr("src");
                extra.put("avatar", src);
                Log.i("parsing", "avatar--->" + src);
            } else if (content.hasClass("author")) {
                extra.put("author", content.text());
                Log.i("parsing", "author--->" + content.text());
            } else if (content.hasClass("bio")) {
                extra.put("bio", content.text());
                Log.i("parsing", "bio--->" + content.text());
            } else if (content.hasClass("content")) {
                extra.put("allcontent", content.html());
                for (Element item : content.children()) {
                    if (!hasImgNode(item) && item.text().length() > 20) {
                        String outerHtml = item.outerHtml().replaceAll("&nbsp;", " ");
                        article.put(outerHtml, "p");
                    } else if (hasImgNode(item)) {
                        Element image = item.child(0);
                        String src = image.attr("src");
                        article.put(src, "img");
                    } else if (item.hasText() && item.text().length() <= 5 && !hasImgNode(item)) {
                        // <p> 标签内容为数字，或者其他简单的东西，传给TextView显示的时候不带标签，正常加粗显示
                        article.put(item.text(), "simpleBoldP");
                    } else if (item.hasText() && item.text().length() > 5 && item.text().length() <= 20 && !hasImgNode(item)) {
                        article.put(item.text(), "simpleP");
                    }
                }
            }
        }
        soup.put("extra", extra);
        soup.put("article", article);
        long after = System.currentTimeMillis();
        Log.d(TAG, "Parse XML used time--->" + (after - before));
        return soup;
    }

    private boolean hasImgNode(Element element) {
        Elements children = element.children();
        for (Element child : children) {
            if (child.nodeName().equals("img")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在html+模式下，对获取到的html数据进行修改，去除不必要的数据
     *
     * @param htmlUrl 原始html字符串
     * @return 之后的html数据
     */
    public Map<String, String> parseHtml(String htmlUrl) {
        Map<String, String> htmlMap = new HashMap<>();
        try {
            Document document = Jsoup.connect(htmlUrl).userAgent("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").get();
            removeElements(document);
            Element header = document.select("div[class=headline]").get(0);
            Elements headerChildren = header.getAllElements();
            for (Element child : headerChildren) {
                if (child.className().equals("headline-title")) {
                    String headline_title = child.text();
                    htmlMap.put("headline_title", headline_title);
                } else if (child.className().equals("img-source")) {
                    String img_source = child.text();
                    htmlMap.put("img_source", img_source);
                } else if (child.nodeName().equals("img")) {
                    String img = child.attr("src");
                    htmlMap.put("img", img);
                }
            }
            header.remove();
            Log.d(TAG, "isLight--->" + ThemeUtils.isLight);
            if (!ThemeUtils.isLight) {
                darkHtml(document);
            }
            String content = document.outerHtml();
            htmlMap.put("content", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlMap;
    }

    /**
     * 通过添加style元素来达到“夜间模式”效果
     *
     * @param document 要修改的Document对象
     */
    private void darkHtml(Document document) {
        String deepDarkFantasy = "";
        String dark = "#403f4d";
        deepDarkFantasy = "<style>\n" +
                "        body{\n" +
                "            background-color:" + dark + " ;\n" +
                "        }\n" +
                "        .main-wrap{\n" +
                "            background-color:" + dark + ";\n" +
                "        }\n" +
                "        .question-title{\n" +
                "            background-color:" + dark + ";\n" +
                "            color: #999;\n" +
                "        }\n" +
                "        .meta .author{\n" +
                "            background-color:" + dark + ";\n" +
                "            color: #999;\n" +
                "        }\n" +
                "        .content{\n" +
                "            background-color:" + dark + ";\n" +
                "            color: #999;\n" +
                "        }\n" +
                "         .question{\n" +
                "            background-color: " + dark + ";\n" +
                "        }\n" +
                "        .footer{\n" +
                "            background-color: " + dark + ";\n" +
                "        }\n" +
                ".question + .question{\n" +
                "            border-top: 5px solid #999;\n" +
                "        }\n" +
                "    </style>";
        document.head().append(deepDarkFantasy);
    }

    /**
     * 避免因为要移除的元素不存在，而造成的IndexOutOfBoundsException，先对元素进行判断
     *
     * @param document 从网页解析得到的Document对象
     */
    private void removeElements(Document document) {
        Elements global_header = document.select("div[class=global-header]");
        if (global_header != null && global_header.size() != 0) {
            global_header.remove();
        }
        Elements header_for_mobile = document.select("div[class=header-for-mobile]");
        if (header_for_mobile != null && header_for_mobile.size() != 0) {
            header_for_mobile.remove();
        }
        Elements question = document.select("div[class=question]");
        if (question != null && question.size() == 2) {
            question.get(1).remove();
        }
        Elements qr = document.select("div[class=qr]").remove();
        if (qr != null && qr.size() == 0) {
            qr.remove();
        }
        Elements bottom_wrap = document.select("div[class=bottom-wrap]");
        if (bottom_wrap != null && bottom_wrap.size() != 0) {
            bottom_wrap.remove();
        }
    }

    /**
     * 清除指定日期前的数据，默认为7天之前
     *
     * @param before 超过此日前的所有数据
     * @return 被删除的数据条数
     */
    public int clearOutdatedDB(int before) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        Cursor findId = database.query("dailyresult"
                , new String[]{"id"}, "date<=?", new String[]{before + ""}, null, null, null);
        List<String> toDelete = new ArrayList<>();
        while (findId.moveToNext()) {
            int id = findId.getInt(findId.getColumnIndex("id"));
            Log.d(TAG, "IDtoDelete in Result--->" + id);
            toDelete.add(id + "");
        }
        if (findId.isAfterLast() && !findId.isClosed()) {
            findId.close();
        }
        int hasDeleted = database.delete("dailyresult", "date<=?", new String[]{before + ""});
        for (String id : toDelete) {
            hasDeleted += database.delete("daily", "id=?", new String[]{id}); //使用delete()一次性删除，会提示参数过多
        }
        Log.d(TAG, "hasDeleted--->" + hasDeleted);
        return hasDeleted;
    }

    /**
     * 删除过期的Glide缓存
     *
     * @param before 过期时间
     */
    public void clearOutdatedPhoto(final int before) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                File cacheDir = Glide.getPhotoCacheDir(App.getContext());
                File[] files = cacheDir.listFiles();
                for (File child : files) {
                    if (Integer.parseInt(Constants.simpleDateFormat.format(child.lastModified())) <= before) {
                        //noinspection ResultOfMethodCallIgnored
                        child.delete();
                    }
                }
                WebActivityPresenter presenter = new WebActivityPresenter();
                presenter.clearCacheFolder(before);
            }
        });
        thread.start();
    }

    @Deprecated
    @Override
    public void saveDailies(List<Daily> dailies, int date) {
        try {
            serializDaily(date, dailies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用SQL语句拼接的插入语句(性能更好)，<b>待测试</b>
     *
     * @param dailyGson 要保存的gson数据
     */
    @Deprecated
    private void insertDailyGsonDB(DailyGson dailyGson) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        String sql = "INSERT OR IGNORE INTO daily(id, title, image_source, image, share_url, ga_prefix, body) values(?,?,?,?,?,?,?)";
        database.execSQL(sql, new Object[]{dailyGson.id, dailyGson.title, dailyGson.image_source, dailyGson.image, dailyGson.share_url, dailyGson.ga_prefix, dailyGson.body});
        database.close();
    }

    /**
     * 使用SQL语句拼接的插入语句(性能更好)，<b>待测试</b>
     *
     * @param dailyResult 要保存的DailyResult数据
     */
    @Deprecated
    private void insertDailyStoriesDB(DailyResult dailyResult) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        for (Daily story : dailyResult.stories) {
            String sql = "INSERT OR IGNORE INTO dailyresult(date, id, title, image, type, ga_prefix) values(?,?,?,?,?,?)";
            database.execSQL(sql, new Object[]{dailyResult.date, story.id, story.title, story.image, story.type, story.ga_prefix});
        }
        database.close();
    }

    /**
     * 序列化List为本地文件，已过时。已使用数据库替代
     *
     * @param date
     * @param dailyList
     * @throws IOException
     */
    @Deprecated
    private void serializDaily(int date, List<Daily> dailyList) throws IOException {
        String itemParentPath = App.getContext().getCacheDir().getAbsolutePath() + "/daily";
        Log.i("itemPath", itemParentPath);
        File itemParent = new File(itemParentPath);
        if (!itemParent.exists()) {
            boolean createdDir = itemParent.mkdir();
            Log.d("serilizDaily", itemParent.getName() + (createdDir ? "创建成功" : "创建失败"));
        }
        File item = new File(itemParentPath + "/" + date);
        FileOutputStream fos = new FileOutputStream(item);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(dailyList);
        oos.close();
    }

    /**
     * 反序列化本地文件为List，已过时。已使用数据库替代
     *
     * @param date
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Deprecated
    private List<Daily> deserializDaily(int date) throws IOException, ClassNotFoundException {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        FileInputStream fis = new FileInputStream(cachePath + "/daily/" + date);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Daily> dailyList = (List<Daily>) ois.readObject();
        ois.close();
        return dailyList;
    }

    @Deprecated
    private boolean hasSerializedObject(int date) {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        File file = new File(cachePath + "/daily/" + date);
        return file.exists();
    }
}
