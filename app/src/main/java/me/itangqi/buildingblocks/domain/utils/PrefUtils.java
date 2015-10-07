package me.itangqi.buildingblocks.domain.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.itangqi.buildingblocks.domain.application.App;

/**
 * 用来快速获取相关的设置
 * Created by Troy on 2015/9/20.
 */
public class PrefUtils {

    private static final String PRE_NAME = "me.itangqi.buildingblocks_preferences";

    public static final String PRE_CRASH_ISLASTTIMECRASHED = "isLastTimeCrashed";
    public static final String PRE_CRASH_URI = "crashUri";

    public static final String PRE_CACHE_ENABLE = "enable_cache";

    public static final String PRE_GSON_OR_HTML = "gson_or_html";

    public static final String PRE_AUTO_UPDATE = "auto_update";


    private static SharedPreferences getSharedPreferences() {
        return App.getContext()
                .getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isEnableCache() {
        return getSharedPreferences().getBoolean(PRE_CACHE_ENABLE, false);
    }

    /**
     * 用来检测设置中“获取数据方式”的值
     *
     * @return 返回"http","http+"或者"gson"
     */
    public static String wayToData() {
        return getSharedPreferences().getString(PRE_GSON_OR_HTML, "http");
    }

    public static boolean isAutoUpdate() {
        return getSharedPreferences().getBoolean(PRE_AUTO_UPDATE, false);
    }

    public static boolean isCrashedLastTime() {
        return getSharedPreferences().getBoolean(PRE_CRASH_ISLASTTIMECRASHED, false);
    }

    public static String getCrashUri() {
        return getSharedPreferences().getString(PRE_CRASH_URI, null);
    }

    public static void setCrash(boolean isLastTimeCrashed, String crashUri) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PRE_CRASH_ISLASTTIMECRASHED, isLastTimeCrashed);
        editor.putString(PRE_CRASH_URI, crashUri);
        editor.commit();
    }

    public static void setCrash(boolean isLastTimeCrashed) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PRE_CRASH_ISLASTTIMECRASHED, isLastTimeCrashed);
        editor.apply();
    }
}
