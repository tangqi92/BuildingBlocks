package me.itangqi.buildingblocks.presenters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.LinkedHashMap;
import java.util.Map;

import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.model.IGsonCallBack;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.view.IGsonNews;
import me.itangqi.buildingblocks.view.ui.activity.GsonViewActivity;

/**
 * Created by Troy on 2015/9/24.
 */
public class GsonNewsPresenter {

    private IGsonNews mIGsonNews;
    private DailyModel mDailyModel;
    private Handler mHandler;
    private IGsonCallBack mCallBack = new IGsonCallBack() {
        @Override
        public void onGsonItemFinish(DailyGson dailyGson) {
            mIGsonNews.loadGson(dailyGson);
        }
    };

    public GsonNewsPresenter(IGsonNews IGsonNews) {
        mIGsonNews = IGsonNews;
        mDailyModel = DailyModel.newInstance(mCallBack);
        mHandler = mIGsonNews.getHandler();
    }

    public void getGsonNews(int id) {
        mDailyModel.getGsonNews(id);
    }

    /**
     * 使用AsyncTask来更新UI
     *
     * @param dailyGson
     * @return
     */
    public Map<String, LinkedHashMap<String, String>> getContentMap(DailyGson dailyGson) {
        return mDailyModel.parseBody(dailyGson);
    }

    /**
     * 使用Handler来更新UI
     *
     * @param dailyGson
     */
    public void startInflater(DailyGson dailyGson) {
        Map<String, LinkedHashMap<String, String>> soup = mDailyModel.parseBody(dailyGson);
        Thread thread = new Thread(new Task(soup));
        thread.start();
    }

    private class Task implements Runnable {

        Map<String, LinkedHashMap<String, String>> mSoup;

        public Task(Map<String, LinkedHashMap<String, String>> soup) {
            mSoup = soup;
        }

        @Override
        public void run() {
            LinkedHashMap<String, String> extra = mSoup.get("extra");
            Message extraMsg = new Message();
            Bundle extraBundle = new Bundle();
            LinkedHashMap<String, String> article = mSoup.get("article");
            for (Map.Entry<String, String> entry : extra.entrySet()) {
                if (entry.getKey().equals("avatar")) {
                    extraBundle.putString("avatar", entry.getValue());
                } else if (entry.getKey().equals("author")) {
                    extraBundle.putString("author", entry.getValue());
                } else if (entry.getKey().equals("bio")) {
                    extraBundle.putString("bio", entry.getValue());
                }
            }
            extraMsg.what = GsonViewActivity.EXTRA;
            extraMsg.setData(extraBundle);
            mHandler.sendMessage(extraMsg);
        }
    }
}
