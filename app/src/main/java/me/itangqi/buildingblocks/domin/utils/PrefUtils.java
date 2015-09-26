package me.itangqi.buildingblocks.domin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.itangqi.buildingblocks.domin.application.App;

/**
 * Created by Troy on 2015/9/20.
 */
public class PrefUtils {

    public static boolean isEnableCache() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean("enable_cache", false);
    }

    public static boolean isUsingGson() {
        SharedPreferences preferences = getSharedPreferences();
        String tmp = preferences.getString("gson_or_html", "http");
        return !tmp.equals("http");
    }

    private static SharedPreferences getSharedPreferences() {
        return App.getContext()
                .getSharedPreferences("me.itangqi.buildingblocks_preferences", Context.MODE_PRIVATE);
    }
}
