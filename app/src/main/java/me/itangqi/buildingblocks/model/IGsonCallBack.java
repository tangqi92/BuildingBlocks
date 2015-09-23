package me.itangqi.buildingblocks.model;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.model.entity.DailyGson;

/**
 * Created by Troy on 2015/9/23.
 */
public interface IGsonCallBack {
    void onGsonItemFinish(DailyGson dailyGson);
}
