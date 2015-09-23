package me.itangqi.buildingblocks.model;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.DailyHttp;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IHttpCallBack {
    void onFinish(List<DailyHttp> dailyList);
}
