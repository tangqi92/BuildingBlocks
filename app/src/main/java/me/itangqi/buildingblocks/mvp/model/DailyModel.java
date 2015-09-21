package me.itangqi.buildingblocks.mvp.model;

import android.support.design.widget.Snackbar;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.itangqi.buildingblocks.api.ZhihuApi;
import me.itangqi.buildingblocks.mvp.entity.Daily;
import me.itangqi.buildingblocks.mvp.entity.DailyResult;
import me.itangqi.buildingblocks.utils.CommonUtils;
import me.itangqi.buildingblocks.utils.NetworkUtils;

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
            if (CommonUtils.hasSerializedObject(date)) {
                mDailiesFromCache = CommonUtils.deserializDaily(date);
            }
            mCallBack.onFinish(mDailiesFromCache);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveDailies(List<Daily> dailies, String date) {
        try {
            CommonUtils.serializDaily(date, dailies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
