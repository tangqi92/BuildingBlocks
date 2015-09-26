package me.itangqi.buildingblocks.domin.utils;

import android.content.Context;
import android.content.pm.PackageManager;

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
}
