package me.itangqi.buildingblocks.view;

import me.itangqi.buildingblocks.model.entity.DailyGson;

/**
 * Created by Troy on 2015/9/24.
 */
public interface IGsonNews {
    void loadGson(DailyGson dailyGson);

    void showProgress();

    void hideProgress();
}
