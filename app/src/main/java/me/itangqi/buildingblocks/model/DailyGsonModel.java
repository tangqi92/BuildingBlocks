package me.itangqi.buildingblocks.model;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

import me.itangqi.buildingblocks.domin.api.ZhihuApi;
import me.itangqi.buildingblocks.model.entity.DailyGsonResult;
import me.itangqi.buildingblocks.model.entity.DailyGsonStory;
import me.itangqi.buildingblocks.model.entity.DailyHttp;

/**
 * Created by Troy on 2015/9/23.
 */
public class DailyGsonModel implements IDaily {

    private List<DailyGsonStory> mDailyGsonStories;
    private IGsonCallBack mICallBack;

    AsyncHttpResponseHandler mAsyncHttpResponseHandler = new BaseJsonHttpResponseHandler<DailyGsonResult>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyGsonResult response) {
            if (response != null && response.mStoryList.size() != 0) {
                for (DailyGsonStory dailyStory : response.mStoryList) {
                    mDailyGsonStories.add(dailyStory);
                }
                mICallBack.onFinish(mDailyGsonStories);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyGsonResult errorResponse) {

        }

        @Override
        protected DailyGsonResult parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyGsonResult.class);
        }
    };




    @Override
    public void getFromNet(String date) {
        String url = ZhihuApi.getDailyNews(date);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, mAsyncHttpResponseHandler);
    }

    @Override
    public void getFromCache(String date) {

    }

    @Override
    public void saveDailies(List<DailyHttp> dailies, String date) {

    }
}
