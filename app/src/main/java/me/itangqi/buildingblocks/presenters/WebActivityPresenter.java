package me.itangqi.buildingblocks.presenters;

import android.util.Log;

import java.io.File;
import java.util.Map;

import me.itangqi.buildingblocks.domain.application.App;
import me.itangqi.buildingblocks.domain.utils.Constants;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.model.IHtmlCallBack;
import me.itangqi.buildingblocks.view.IWebView;

/**
 * Created by Troy on 2015/9/21.
 */
public class WebActivityPresenter {

    private IWebView mWebView;
    private File cacheDir;
    private DailyModel mDailyModel;
    private long mDeletedSize;
    private static WebActivityPresenter mPresenter;
    private IHtmlCallBack mIHtmlCallBack;

    public WebActivityPresenter(IWebView webView) {
        this.mWebView = webView;
        this.cacheDir = mWebView.getWebViewCacheDir();
        mDailyModel = DailyModel.newInstance();
        this.mIHtmlCallBack = new IHtmlCallBack() {
            @Override
            public void onFinish(Map<String, String> map) {
                mWebView.loadBetterHtml(map);
            }
        };
    }

    public WebActivityPresenter() {
        this.cacheDir = App.getContext().getCacheDir();
    }

    public long clearCacheFolder() {
        Log.d("CachePath", "WebViewCachePath--->" + cacheDir.getAbsolutePath());
        Log.d("canWrite?", "目录是否可写？--->" + (cacheDir.canWrite() ? "是" : "否"));
        deleteFiles(cacheDir);
        return mDeletedSize;
    }

    public long clearCacheFolder(int before) {
        File webCache = new File(cacheDir, "org.chromium.android_webview");
        File[] files = webCache.listFiles();
        long clearedSize = 0;
        if (files != null && files.length != 0) {
            for (File child : files) {
                if (Integer.parseInt(Constants.simpleDateFormat.format(child.lastModified())) <= before) {
                    clearedSize += child.length();
                    //noinspection ResultOfMethodCallIgnored
                    child.delete();
                }
            }
        }
        return clearedSize;
    }

    private void deleteFiles(File file) {
        for (File child : file.listFiles()) {
            if (child.isDirectory()) {
                deleteFiles(child);
            } else if (child.isFile()) {
                Log.d("fileName", child.getName());
                mDeletedSize += child.length();
                boolean isDeleted = child.delete();
                Log.d("isDeleted", isDeleted ? "删除成功" : "删除失败");
            }
        }
    }

    public void getDailyGson(int id) {
        mDailyModel.getGsonNews(id);
    }

    public void getBetterHtml(final String htmlUrl) {
        new Thread() {
            @Override
            public void run() {
                Map<String, String> map = mDailyModel.parseHtml(htmlUrl);
                mIHtmlCallBack.onFinish(map);
            }
        }.start();
    }
}
