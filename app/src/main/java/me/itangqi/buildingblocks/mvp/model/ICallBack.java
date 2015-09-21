package me.itangqi.buildingblocks.mvp.model;

import java.util.List;

import me.itangqi.buildingblocks.mvp.entity.Daily;

/**
 * Created by Troy on 2015/9/21.
 */
public interface ICallBack {
    void onFinish(List<Daily> dailyList);
}
