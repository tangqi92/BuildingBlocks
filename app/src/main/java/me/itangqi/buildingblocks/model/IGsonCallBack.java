package me.itangqi.buildingblocks.model;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.DailyGsonStory;

/**
 * Created by Troy on 2015/9/23.
 */
public interface IGsonCallBack {
    void onFinish(List<DailyGsonStory> dailyGsonStories);
}
