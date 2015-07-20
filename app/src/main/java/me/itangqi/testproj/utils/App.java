package me.itangqi.testproj.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {
    private static App mContext;
    private RefWatcher mRefWatcher;
    /**
     * 开发测试模式
     */
    private static final boolean DEVELOPER_MODE = true;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    public static App getInstance() {
        return mContext;
    }
}
