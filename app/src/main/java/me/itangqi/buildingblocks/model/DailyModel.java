package me.itangqi.buildingblocks.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import me.itangqi.buildingblocks.domin.api.ZhihuApi;
import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.domin.db.SQLiteHelper;
import me.itangqi.buildingblocks.domin.utils.PrefUtils;
import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.model.entity.DailyResult;

/**
 * Created by Troy on 2015/9/21.
 */
public class DailyModel implements IDaily {

    private List<Daily> mDailiesFromNet;
    private List<Daily> mDailiesFromCache;
    private IHttpCallBack mIHttpCallBack;
    private IGsonCallBack mIGsonCallBack;
//    private SparseArray<String> mItems = new SparseArray<>();  //待测试
    private SQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDatabase;
    private static DailyModel mDailyModel;

    //用来获取Daily集合
    AsyncHttpResponseHandler mAsyncHttpResponseHandler = new BaseJsonHttpResponseHandler<DailyResult>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyResult response) {
            if (response != null && response.stories.size() != 0) {
                for (Daily daily : response.stories) {
                    mDailiesFromNet.add(daily);
//                    mItems.put(daily.id, daily.title);   //待测试
                }
                mIHttpCallBack.onFinish(mDailiesFromNet);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyResult errorResponse) {

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

        }

        @Override
        protected DailyGson parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyGson.class);
        }
    };

    public static DailyModel newInstance(IHttpCallBack iHttpCallBack) {
        if (mDailyModel == null) {
            return new DailyModel(iHttpCallBack);
        } else {
            return mDailyModel;
        }
    }

    public static DailyModel newInstance(IGsonCallBack iGsonCallBack) {
        if (mDailyModel == null) {
            return new DailyModel(iGsonCallBack);
        } else {
            return mDailyModel;
        }
    }

    private DailyModel() {
        mSQLiteHelper = new SQLiteHelper(App.getContext(), "zhihu.db", null, 1);
    }

    private DailyModel(IHttpCallBack IHttpCallBack) {
        this();
        this.mIHttpCallBack = IHttpCallBack;
        this.mDailiesFromNet = new ArrayList<>();
        this.mDailiesFromCache = new ArrayList<>();
    }

    private DailyModel(IGsonCallBack IGsonCallBack) {
        this();
        this.mIGsonCallBack = IGsonCallBack;
        this.mDailiesFromNet = new ArrayList<>();
        this.mDailiesFromCache = new ArrayList<>();
    }

    @Override
    public void getFromNet(String date) {
        String url = ZhihuApi.getDailyNews(date);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, mAsyncHttpResponseHandler);
    }

    @Override
    public void getFromCache(String date) {
        try {
            if (hasSerializedObject(date)) {
                mDailiesFromCache = deserializDaily(date);
            }
            mIHttpCallBack.onFinish(mDailiesFromCache);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveDailies(List<Daily> dailies, String date) {
        try {
            serializDaily(date, dailies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getGsonNews(int id) {
        if (getDailyGsonDB(id) == null) {
            String url = ZhihuApi.getGsonNewsContent(id);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, mGsonNewsResponseHandler);
        }
    }


//    "id smallint primary key,"
//            "title text,"
//            "image_source text,"
//            "image text,"
//            "share_url text,"
//            "ga_prefix int,"
//            "body text)";
    private void saveDailyGsonDB(DailyGson daily) {
        remakeDBObject(true);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", daily.getId());
        contentValues.put("title", daily.getTitle());
        contentValues.put("type", daily.getTitle());
        contentValues.put("image_source", daily.getImage_source());
        contentValues.put("image", daily.getImage());
        contentValues.put("share_url", daily.getShare_url());
        contentValues.put("ga_prefix", daily.getGa_Prefix());
        contentValues.put("body", daily.getBody());
        mDatabase.beginTransaction();
        try {
            mDatabase.insert("daily", null, contentValues);
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabase.endTransaction();
            mDatabase.close();
        }
    }

    private DailyGson getDailyGsonDB(int id) {
        remakeDBObject(false);
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM daily where id = ?", new String[]{id + ""});
        DailyGson dailyGson = new DailyGson();
        if (cursor.moveToFirst()) {
            dailyGson.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dailyGson.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            dailyGson.setType(cursor.getInt(cursor.getColumnIndex("type")));
            dailyGson.setImage_source(cursor.getString(cursor.getColumnIndex("image_source")));
            dailyGson.setImage(cursor.getString(cursor.getColumnIndex("image")));
            dailyGson.setShare_url(cursor.getString(cursor.getColumnIndex("share_url")));
            dailyGson.setGa_prefix(cursor.getInt(cursor.getColumnIndex("ga_prefix")));
            dailyGson.setBody(cursor.getString(cursor.getColumnIndex("body")));
            cursor.close();
            mIGsonCallBack.onGsonItemFinish(dailyGson);
            return dailyGson;
        }
        return null;
    }

    @Deprecated
    private void serializDaily(String date, List<Daily> dailyList) throws IOException {
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

    @Deprecated
    private List<Daily> deserializDaily(String date) throws IOException, ClassNotFoundException {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        FileInputStream fis = new FileInputStream(cachePath + "/daily/" + date);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Daily> dailyList = (List<Daily>) ois.readObject();
        ois.close();
        return dailyList;
    }

    @Deprecated
    private boolean hasSerializedObject(String date) {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        File file = new File(cachePath + "/daily/" + date);
        return file.exists();
    }

    private void remakeDBObject(boolean writable) {
        if (mDatabase == null) {
            if (writable) {
                mDatabase = mSQLiteHelper.getWritableDatabase();
            } else {
                mDatabase = mSQLiteHelper.getReadableDatabase();
            }
        }
    }

}
