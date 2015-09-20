package me.itangqi.buildingblocks.application;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends LitePalApplication {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
