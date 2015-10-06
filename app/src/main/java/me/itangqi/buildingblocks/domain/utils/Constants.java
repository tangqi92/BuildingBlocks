package me.itangqi.buildingblocks.domain.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {

    public static final int PAGE_COUNT = 7;

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);

    public static final String BROADCAST_UPDATE_ACTION = "me.itangqi.buildingblocks.broadcast.update.action";

    public static final String BROADCAST_STATUS = "me.itangqi.buildingblocks.broadcast.update.status";

    public static final String BROADCAST_UPDATE_CATEGORY = "me.itangqi.buildingblocks.broadcast.update.category";
}