package me.itangqi.buildingblocks.domain.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by tangqi on 10/7/15.
 */
public class DeviceUtils {

    public static List<String> getDeviceMsg(Context mContext) {
        List<String> deviceInfo = new ArrayList<>();
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            deviceInfo.add("=====\tDevice Info\t=====");
            deviceInfo.add("manufacture:" + Build.MANUFACTURER);
            deviceInfo.add("product:" + Build.PRODUCT);
            deviceInfo.add("model:" + Build.MODEL);
            deviceInfo.add("version.release:" + Build.VERSION.RELEASE);
            deviceInfo.add("version:" + Build.DISPLAY);
            deviceInfo.add("=====\tBB Info\t=====");
            deviceInfo.add("versionCode:" + info.versionCode + "");
            deviceInfo.add("versionName:" + info.versionName);
            deviceInfo.add("lastUpdateTime:" + new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒", Locale.CHINA).format(info.lastUpdateTime));
        }
        return deviceInfo;
    }
}
