package me.itangqi.buildingblocks.utils;

import android.content.Intent;
import android.content.pm.PackageManager;

import me.itangqi.buildingblocks.application.App;


public final class Check {
    private Check() {

    }

    public static boolean isZhihuClientInstalled() {
        try {
            return preparePackageManager().getPackageInfo("com.zhihu.android", PackageManager.GET_ACTIVITIES) != null;
        } catch (PackageManager.NameNotFoundException ignored) {
            return false;
        }
    }

    public static boolean isIntentSafe(Intent intent) {
        return preparePackageManager().queryIntentActivities(intent, 0).size() > 0;
    }

    private static PackageManager preparePackageManager() {
        return App.mContext.getPackageManager();
    }
}
