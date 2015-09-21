package me.itangqi.buildingblocks.presenters;

import android.util.Log;

import org.litepal.util.LogUtil;

import java.io.File;

import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.view.IWebView;

/**
 * Created by Troy on 2015/9/21.
 */
public class WebActivityPresenter {

    IWebView mWebView;
    File cacheDir;

    public WebActivityPresenter(IWebView webView) {
        mWebView = webView;
        cacheDir = mWebView.getWebViewCacheDir();
    }

    public WebActivityPresenter() {
        cacheDir = App.getContext().getCacheDir();
    }

    public long clearCacheFolder() {
        Log.d("CachePath", "WebViewCachePath--->" + cacheDir.getAbsolutePath());
        long deletedSize = 0;
        Log.d("canWrite?", "目录是否可写？--->" + (cacheDir.canWrite() ? "是" : "否"));
        deletedSize += deleteFiles(cacheDir);
        return deletedSize;
    }

    private long deleteFiles(File file) {
        long deletedSize = 0;
        for (File child : file.listFiles()) {
            Log.d("fileName", child.getName());
            if (child.isDirectory()) {
                deleteFiles(child);
            } else {
                deletedSize += child.length();
                boolean isDeleted = child.delete();
                Log.d("isDeleted", isDeleted ? "删除成功" : "删除失败");
            }
        }
        return deletedSize;
    }

}
