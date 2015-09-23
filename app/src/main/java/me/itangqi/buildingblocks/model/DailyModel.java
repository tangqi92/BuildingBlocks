package me.itangqi.buildingblocks.model;

import android.util.Log;

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
import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.model.entity.DailyResult;
import me.itangqi.buildingblocks.domin.utils.CommonUtils;

/**
 * Created by Troy on 2015/9/21.
 */
public class DailyModel implements IDaily {

    private List<Daily> mDailiesFromNet;
    private List<Daily> mDailiesFromCache;
    private ICallBack mCallBack;

    AsyncHttpResponseHandler mAsyncHttpResponseHandler = new BaseJsonHttpResponseHandler<DailyResult>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyResult response) {
            if (response != null && response.stories.size() != 0) {
                for (Daily daily : response.stories) {
                    mDailiesFromNet.add(daily);
                }
                mCallBack.onFinish(mDailiesFromNet);
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

    public DailyModel(ICallBack callBack) {
        this.mCallBack = callBack;
        mDailiesFromNet = new ArrayList<>();
        mDailiesFromCache = new ArrayList<>();
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
            mCallBack.onFinish(mDailiesFromCache);
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

    private List<Daily> deserializDaily(String date) throws IOException, ClassNotFoundException {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        FileInputStream fis = new FileInputStream(cachePath + "/daily/" + date);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Daily> dailyList = (List<Daily>) ois.readObject();
        ois.close();
        return dailyList;
    }

    private boolean hasSerializedObject(String date) {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        File file = new File(cachePath + "/daily/" + date);
        return file.exists();
    }

}
