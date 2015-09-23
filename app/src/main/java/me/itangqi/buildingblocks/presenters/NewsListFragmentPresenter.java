package me.itangqi.buildingblocks.presenters;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.buildingblocks.model.entity.DailyHttp;
import me.itangqi.buildingblocks.model.DailyHttpModel;
import me.itangqi.buildingblocks.model.IHttpCallBack;
import me.itangqi.buildingblocks.view.IViewPager;
import me.itangqi.buildingblocks.domin.utils.NetworkUtils;
import me.itangqi.buildingblocks.domin.utils.PrefUtils;

/**
 * Created by Troy on 2015/9/21.
 */
public class NewsListFragmentPresenter {
    private DailyHttpModel mDailyModel;
    private IViewPager mIViewPager;
    private List<DailyHttp> mDailyHttpList;
    private List<DailyHttp> mCacheList;
    private String date;

    public NewsListFragmentPresenter(IViewPager IViewPager, final String date) {
        this.mIViewPager = IViewPager;
        this.date = date;
        this.mDailyHttpList = new ArrayList<>();
        this.mCacheList = new ArrayList<>();
        mDailyModel = new DailyHttpModel(new IHttpCallBack() {
            @Override
            public void onFinish(List<DailyHttp> dailyHttpList) {
                mDailyModel.saveDailies(dailyHttpList, date);
                loadData(dailyHttpList);
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

    public void loadData(List<DailyHttp> dailies) {
        mIViewPager.loadData(dailies);
    }

}
