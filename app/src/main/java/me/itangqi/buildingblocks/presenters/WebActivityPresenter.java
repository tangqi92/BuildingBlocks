package me.itangqi.buildingblocks.presenters;

import android.util.Log;

import java.io.File;

import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.model.IGsonCallBack;
import me.itangqi.buildingblocks.model.entity.DailyGson;
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

    private IGsonCallBack mIGsonCallBack = new IGsonCallBack() {
        @Override
        public void onGsonItemFinish(DailyGson dailyGson) {
            mWebView.loadGsonNews(dailyGson);
        }
    };

    private WebActivityPresenter(IWebView webView) {
        this.mWebView = webView;
        this.cacheDir = mWebView.getWebViewCacheDir();
        mDailyModel = DailyModel.newInstance(mIGsonCallBack);
    }

    private WebActivityPresenter() {
        this.cacheDir = App.getContext().getCacheDir();
    }

    public static WebActivityPresenter newInstance() {
        if (mPresenter == null) {
            return new WebActivityPresenter();
        } else {
            return mPresenter;
        }
    }

    public static WebActivityPresenter newInstance(IWebView webView) {
        if (mPresenter == null || mPresenter.mWebView == null) {
            return new WebActivityPresenter(webView);
        } else {
            return mPresenter;
        }
    }

    public long clearCacheFolder() {
        Log.d("CachePath", "WebViewCachePath--->" + cacheDir.getAbsolutePath());
        Log.d("canWrite?", "目录是否可写？--->" + (cacheDir.canWrite() ? "是" : "否"));
        deleteFiles(cacheDir);
        return mDeletedSize;
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

}
