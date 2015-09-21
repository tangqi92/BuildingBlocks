package me.itangqi.buildingblocks.mvp.view;

import java.util.List;

import me.itangqi.buildingblocks.mvp.bean.Daily;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IViewPager {
    List<Daily> getNewsContent();

    void showProgress();

    void hideProgress();
}
