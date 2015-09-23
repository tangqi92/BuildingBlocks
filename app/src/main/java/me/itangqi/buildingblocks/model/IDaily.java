package me.itangqi.buildingblocks.model;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.Daily;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IDaily {
    void getFromNet(String date);

    void getFromCache(String date);

    void saveDailies(List<Daily> dailies, String date);

    void getGsonNews(int id);
}
