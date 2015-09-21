package me.itangqi.buildingblocks.mvp.presenters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.itangqi.buildingblocks.mvp.entity.Daily;
import me.itangqi.buildingblocks.mvp.model.DailyModel;
import me.itangqi.buildingblocks.mvp.model.ICallBack;
import me.itangqi.buildingblocks.mvp.view.IViewPager;
import me.itangqi.buildingblocks.utils.CommonUtils;
import me.itangqi.buildingblocks.utils.NetworkUtils;
import me.itangqi.buildingblocks.utils.PrefUtils;

/**
 * Created by Troy on 2015/9/21.
 */
public class NewsListFragmentPresenter {
    private DailyModel mDailyModel;
    private IViewPager mIViewPager;
    private List<Daily> mDailyList;
    private List<Daily> mCacheList;
    private String date;

    public NewsListFragmentPresenter(IViewPager IViewPager, final String date) {
        this.mIViewPager = IViewPager;
        this.date = date;
        this.mDailyList = new ArrayList<>();
        this.mCacheList = new ArrayList<>();
        mDailyModel = new DailyModel(new ICallBack() {
            @Override
            public void onFinish(List<Daily> dailyList) {
                mDailyModel.saveDailies(dailyList, date);
                loadData(dailyList);
            }
        });
    }

    public void getNews(String date) {
        if (PrefUtils.isEnableCache()) {
            mDailyModel.getFromCache(date);
            mDailyModel.getFromNet(date);
        }else if (!PrefUtils.isEnableCache() && NetworkUtils.isNetworkConnected()) {
            mDailyModel.getFromNet(date);
        }else if (PrefUtils.isEnableCache() && !NetworkUtils.isNetworkConnected()) {
            mDailyModel.getFromCache(date);
        }
    }

    public void loadData(List<Daily> dailies) {
        mIViewPager.loadData(dailies);
    }

}
