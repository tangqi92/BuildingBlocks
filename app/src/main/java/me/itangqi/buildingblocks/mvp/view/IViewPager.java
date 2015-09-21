package me.itangqi.buildingblocks.mvp.view;

import java.util.List;

import me.itangqi.buildingblocks.mvp.entity.Daily;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IViewPager {

    void loadData(List<Daily> dailies);

    void showProgress();

    void hideProgress();

    void failload();

    void networkunavaiable();

    void notifyNewsChanged();
}
