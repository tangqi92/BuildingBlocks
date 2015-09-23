package me.itangqi.buildingblocks.presenters;

import android.text.LoginFilter;
import android.util.Log;

import org.litepal.crud.DataSupport;
import org.litepal.util.LogUtil;

import java.io.File;
import java.util.List;

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

    private IGsonCallBack mIGsonCallBack = new IGsonCallBack() {
        @Override
        public void onGsonItemFinish(DailyGson dailyGson) {
            mWebView.loadGsonNews(dailyGson);
        }
    };

    public WebActivityPresenter(IWebView webView) {
        this.mWebView = webView;
        this.cacheDir = mWebView.getWebViewCacheDir();
        mDailyModel = new DailyModel(mIGsonCallBack);
    }

    public WebActivityPresenter() {
        this.cacheDir = App.getContext().getCacheDir();
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

    public void getDailyGson(int id) {
        DailyGson dailyGson = DataSupport.find(DailyGson.class, id);
        if (dailyGson != null) {
            Log.d("WebPresenter", "read DailyFromDB" + dailyGson.getId());
            mWebView.loadGsonNews(dailyGson);
        } else {
            mDailyModel.getGsonNews(id);
        }
    }

}
