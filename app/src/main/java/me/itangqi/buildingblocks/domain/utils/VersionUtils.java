package me.itangqi.buildingblocks.domain.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import me.itangqi.buildingblocks.domain.application.App;

/**
 * Created by tangqi on 9/25/15.
 */
public class VersionUtils {

    public static String setUpVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVerisonCode() {
        int versionCode = 0;
        try {
            versionCode = App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
