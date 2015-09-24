package me.itangqi.buildingblocks.presenters;

import java.util.LinkedHashMap;
import java.util.Map;

import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.model.IGsonCallBack;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.view.IGsonNews;

/**
 * Created by Troy on 2015/9/24.
 */
public class GsonNewsPresenter {

    private IGsonNews mIGsonNews;
    private DailyModel mDailyModel;
    private IGsonCallBack mCallBack = new IGsonCallBack() {
        @Override
        public void onGsonItemFinish(DailyGson dailyGson) {
            mIGsonNews.loadGson(dailyGson);
        }
    };

    public GsonNewsPresenter(IGsonNews IGsonNews) {
        mIGsonNews = IGsonNews;
        mDailyModel = DailyModel.newInstance(mCallBack);
    }

    public void getGsonNews(int id) {
        mDailyModel.getGsonNews(id);
    }

    public Map<String, LinkedHashMap<String, String>> getContentMap(DailyGson dailyGson) {
        Map<String, LinkedHashMap<String, String>> soup = mDailyModel.parseBody(dailyGson);
        return soup;
    }
}
