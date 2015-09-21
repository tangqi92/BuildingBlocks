package me.itangqi.buildingblocks.domin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.itangqi.buildingblocks.domin.application.App;

/**
 * Created by Troy on 2015/9/20.
 */
public class PrefUtils {
    public static boolean isEnableCache() {
        SharedPreferences preferences = App.getContext()
                .getSharedPreferences("me.itangqi.buildingblocks_preferences", Context.MODE_PRIVATE);
        return preferences.getBoolean("enable_cache", false);
    }
}
