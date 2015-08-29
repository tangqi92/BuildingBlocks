package me.itangqi.buildingblocks.api;

/*
 * Thanks to
 * https://github.com/izzyleung/ZhihuDailyPurify/wiki/知乎日报-API-分析
 * Author: izzyleung
 */
public final class ZhihuApi {

    public static final String ZHIHU_DAILY_NEWS = "http://news.at.zhihu.com/api/4/news/before/";
    public static final String ZHIHU_DAILY_NEWS_CONTENT = "http://daily.zhihu.com/story/";

    // getDailyNews GET
    public static String getDailyNews(String date) {
        return ZHIHU_DAILY_NEWS + date;
    }

    // getDailyNewsContent GET
    public static String getNewsContent(int id) {
        return ZHIHU_DAILY_NEWS_CONTENT + id;
    }

}
