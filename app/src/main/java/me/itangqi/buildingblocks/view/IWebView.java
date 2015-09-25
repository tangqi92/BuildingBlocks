package me.itangqi.buildingblocks.view;

import java.io.File;

import me.itangqi.buildingblocks.model.entity.DailyGson;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IWebView {

    File getWebViewCacheDir();

    void loadGsonNews(DailyGson dailyGson);

}
