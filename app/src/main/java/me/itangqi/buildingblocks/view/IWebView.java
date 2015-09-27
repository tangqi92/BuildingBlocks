package me.itangqi.buildingblocks.view;

import java.io.File;
import java.util.Map;

import me.itangqi.buildingblocks.model.entity.DailyGson;

/**
 * Created by Troy on 2015/9/21.
 */
public interface IWebView {

    File getWebViewCacheDir();

    void loadBetterHtml(Map<String, String> htmlMap);
}
