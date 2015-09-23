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
import me.itangqi.buildingblocks.model.entity.DailyHttp;
import me.itangqi.buildingblocks.model.entity.DailyHttpResult;

/**
 * Created by Troy on 2015/9/21.
 */
public class DailyHttpModel implements IDaily {

    private List<DailyHttp> mDailiesFromNet;
    private List<DailyHttp> mDailiesFromCache;
    private IHttpCallBack mCallBack;

    AsyncHttpResponseHandler mAsyncHttpResponseHandler = new BaseJsonHttpResponseHandler<DailyHttpResult>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyHttpResult response) {
            if (response != null && response.stories.size() != 0) {
                for (DailyHttp dailyHttp : response.stories) {
                    mDailiesFromNet.add(dailyHttp);
                }
                mCallBack.onFinish(mDailiesFromNet);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyHttpResult errorResponse) {

        }

        @Override
        protected DailyHttpResult parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyHttpResult.class);
        }
    };

    public DailyHttpModel(IHttpCallBack callBack) {
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
    public void saveDailies(List<DailyHttp> dailies, String date) {
        try {
            serializDaily(date, dailies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serializDaily(String date, List<DailyHttp> dailyHttpList) throws IOException {
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
        oos.writeObject(dailyHttpList);
        oos.close();
    }

    private List<DailyHttp> deserializDaily(String date) throws IOException, ClassNotFoundException {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        FileInputStream fis = new FileInputStream(cachePath + "/daily/" + date);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<DailyHttp> dailyHttpList = (List<DailyHttp>) ois.readObject();
        ois.close();
        return dailyHttpList;
    }

    private boolean hasSerializedObject(String date) {
        String cachePath = App.getContext().getCacheDir().getAbsolutePath();
        File file = new File(cachePath + "/daily/" + date);
        return file.exists();
    }

}
