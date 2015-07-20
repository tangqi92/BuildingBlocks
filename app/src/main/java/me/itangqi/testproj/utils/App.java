package me.itangqi.testproj.utils;

import android.app.Application;

/**
 *
 * @author bxbxbai
 */
public class App extends Application {
    private static App mContext;
//    private RefWatcher mRefWatcher;
    /**
     * 开发测试模式
     */
    private static final boolean DEVELOPER_MODE = true;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        mRefWatcher = LeakCanary.install(this);


//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//        .detectDiskReads()
//        .detectDiskWrites()
//        .detectNetwork()
//        .penaltyLog()
//        .build());
//
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//        .detectActivityLeaks()
//        .detectLeakedSqlLiteObjects()
//        .penaltyLog()
//        .penaltyDeath()
//        .build());

//        initStetho();

    }

//    public static RefWatcher getRefWatcher() {
//        return getInstance().mRefWatcher;
//    }

//    private void initStetho() {
//        Stetho.initialize(Stetho.newInitializerBuilder(this)
//                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                .build());
//    }

    public static App getInstance() {
        return mContext;
    }
}
