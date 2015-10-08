package me.itangqi.buildingblocks.domain.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.buildingblocks.domain.utils.CrashCatcher;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {

    private static Context mContext;
    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        CrashCatcher crashCatcher = CrashCatcher.newInstance();
        crashCatcher.setDefaultCrashCatcher();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }

    public static void exitAll() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
        System.exit(0);
    }
}
