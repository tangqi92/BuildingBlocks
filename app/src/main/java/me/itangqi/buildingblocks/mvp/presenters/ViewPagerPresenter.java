package me.itangqi.buildingblocks.mvp.presenters;

import java.util.List;

import me.itangqi.buildingblocks.mvp.bean.Daily;
import me.itangqi.buildingblocks.mvp.model.DailyModel;
import me.itangqi.buildingblocks.mvp.view.IViewPager;
import me.itangqi.buildingblocks.utils.PrefUtils;

/**
 * Created by Troy on 2015/9/21.
 */
public class ViewPagerPresenter {
    private DailyModel mDailyModel;
    private IViewPager mIViewPager;
    private String date;

    public ViewPagerPresenter(IViewPager IViewPager, String date) {
        mIViewPager = IViewPager;
        this.date = date;
        mDailyModel = new DailyModel();
    }

    public List<Daily> getNewsContent() {
        List<Daily> dailies;
        if (PrefUtils.isEnableCache()) {
            dailies = mDailyModel.getFromCache(date);
        }else {
            dailies = mDailyModel.getFromNet(date);
        }
        return dailies;
    }

}
