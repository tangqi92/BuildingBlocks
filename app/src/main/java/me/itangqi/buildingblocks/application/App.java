package me.itangqi.buildingblocks.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by tangqi on 7/20/15.
 */
public class App extends Application {
    public static App mContext;
    public static RequestQueue requestQueue;
    public static int memoryCacheSize;
    /**
     * 开发测试模式
     */
    private static final boolean DEVELOPER_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // 不必为每一次HTTP请求都创建一个RequestQueue对象，推荐在application中初始化
        requestQueue = Volley.newRequestQueue(this);
        // 计算内存缓存
        memoryCacheSize = getMemoryCacheSize();
    }

    public static App getInstance() {
        return mContext;
    }

    /**
     * @paramcontext
     * @return 得到需要分配的缓存大小，这里用八分之一的大小来做
     * @description
     */
    public int getMemoryCacheSize() {
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // Use 1/8th of the available memory for this memory cache.
        return maxMemory / 8;
    }
}
