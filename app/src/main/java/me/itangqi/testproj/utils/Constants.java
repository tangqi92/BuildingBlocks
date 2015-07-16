package me.itangqi.testproj.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {
    private Constants() {

    }

    public static final class Url {
        public static final String ZHIHU_DAILY_BEFORE = "http://news.at.zhihu.com/api/3/news/before/";
        public static final String ZHIHU_DAILY_OFFLINE_NEWS = "http://news-at.zhihu.com/api/3/news/";
        public static final String ZHIHU_DAILY_PURIFY_BEFORE = "http://zhihu-daily-purify.herokuapp.com/raw/";
        public static final String SEARCH = "http://zhihu-daily-purify.herokuapp.com/search/";
    }

    public static final class Date {
        public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        @SuppressWarnings("deprecation")
        public static final java.util.Date birthday = new java.util.Date(113, 4, 19); // May 19th, 2013
    }

}
