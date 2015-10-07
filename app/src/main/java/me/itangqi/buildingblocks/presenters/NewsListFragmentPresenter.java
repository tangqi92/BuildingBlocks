package me.itangqi.buildingblocks.presenters;

import java.util.List;

import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.model.IHttpCallBack;
import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.view.IViewPager;

/**
 * Created by Troy on 2015/9/21.
 */
public class NewsListFragmentPresenter {
    private DailyModel mDailyModel;
    private IViewPager mIViewPager;
    private int date;

    public NewsListFragmentPresenter(IViewPager IViewPager, final int date) {
        this.mIViewPager = IViewPager;
        this.date = date;
        mDailyModel = DailyModel.newInstance(new IHttpCallBack() {
            @Override
            public void onFinish(List<Daily> dailyList) {
//                mDailyModel.saveDailies(dailyList, date);  //数据储存在Volly获取数据后立即进行，不单独调用saveDailies()
                loadData(dailyList);
            }
        });
    }

    public void getNews(int date) {
        mDailyModel.getDailyResult(date);
    }

    public void loadData(List<Daily> dailies) {
        mIViewPager.loadData(dailies);
    }
}
