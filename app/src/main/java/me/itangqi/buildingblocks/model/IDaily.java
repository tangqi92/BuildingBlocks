package me.itangqi.buildingblocks.model;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.Daily;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IDaily {

    void getDailyResult(int date);

    /**
     * 保存序列化集合的方法
     * @param dailies
     * @param date
     */
    @Deprecated
    void saveDailies(List<Daily> dailies, int date);

    void getGsonNews(int id);
}
