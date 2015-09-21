package me.itangqi.buildingblocks.mvp.model;

import java.util.List;

import me.itangqi.buildingblocks.mvp.bean.Daily;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IDaily {
    List<Daily> getFromNet(String date);

    List<Daily> getFromCache(String date);

    List<Daily> getDailiesForAdapter();

    void saveDailies(List<Daily> dailies, String date);

}
