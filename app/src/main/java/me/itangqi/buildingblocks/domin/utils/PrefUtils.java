package me.itangqi.buildingblocks.domin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.itangqi.buildingblocks.domin.application.App;

/**
 * 用来快速获取相关的设置
 * Created by Troy on 2015/9/20.
 */
public class PrefUtils {

    public static boolean isEnableCache() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean("enable_cache", false);
    }

    /**
     * 用来检测设置中“获取数据方式”的值
     * @return 返回"http","http+"或者"gson"
     */
    public static String wayToData() {
        SharedPreferences preferences = getSharedPreferences();
        String tmp = preferences.getString("gson_or_html", "http");
        return tmp;
    }

    private static SharedPreferences getSharedPreferences() {
        return App.getContext()
                .getSharedPreferences("me.itangqi.buildingblocks_preferences", Context.MODE_PRIVATE);
    }
}
