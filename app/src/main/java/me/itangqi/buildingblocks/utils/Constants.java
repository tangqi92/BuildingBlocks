package me.itangqi.buildingblocks.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {

    public static final String VERSION_NAME = "0.1.1";

    public static final class Date {
        public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        @SuppressWarnings("deprecation")
        public static final java.util.Date birthday = new java.util.Date(113, 4, 19); // May 19th, 2013
    }

}
