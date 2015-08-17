package me.itangqi.buildingblocks.application;

import android.app.Application;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {

    public static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static App getInstance() {
        return mContext;
    }
}
