package me.itangqi.buildingblocks.domain.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {

    private static Context get;
    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        get = this;
        CrashCatcher crashCatcher = CrashCatcher.newInstance();
        crashCatcher.setDefaultCrashCatcher();
    }

    public static void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public static void exit() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
        System.exit(0);
    }

    public static Context getContext() {
        return get;
    }
}
