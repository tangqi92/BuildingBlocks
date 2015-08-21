package me.itangqi.buildingblocks.api;

import android.content.Context;

/*
 * https://github.com/izzyleung/ZhihuDailyPurify/wiki/知乎日报-API-分析
 * Author: izzyleung
 */
public final class ZhihuApi {

    public static final String ZHIHU_DAILY_NEWS = "http://news.at.zhihu.com/api/4/news/before/";

    // getDailyNews GET
    public static String getDailyNews(Context context, String date) {
        return ZHIHU_DAILY_NEWS + date;
    }

}
