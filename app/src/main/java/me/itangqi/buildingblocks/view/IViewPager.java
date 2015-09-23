package me.itangqi.buildingblocks.view;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.DailyHttp;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IViewPager {

    void loadData(List<DailyHttp> dailies);

    void showProgress();

    void hideProgress();

    void failload();

    void networkunavaiable();
}
