package me.itangqi.buildingblocks.presenters;

import org.litepal.util.LogUtil;

import java.io.File;

import me.itangqi.buildingblocks.view.IWebView;

/**
 * Created by Troy on 2015/9/21.
 */
public class WebActivityPresenter {

    IWebView mWebView;

    public WebActivityPresenter(IWebView webView) {
        mWebView = webView;
    }

    public long clearCacheFolder() {
        File cacheDir = mWebView.getWebViewCacheDir();
        long deletedSize = 0;
        if (cacheDir != null && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                deletedSize += file.length();
                boolean isDeleted = file.delete();
                LogUtil.d("isDeleted", isDeleted ? "删除成功" : "删除失败");
            }
        }
        return deletedSize;
    }

}
