package me.itangqi.buildingblocks.view.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.utils.CommonUtils;
import me.itangqi.buildingblocks.domin.utils.PrefUtils;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.presenters.WebActivityPresenter;
import me.itangqi.buildingblocks.domin.utils.NetworkUtils;
import me.itangqi.buildingblocks.view.IWebView;
import me.itangqi.buildingblocks.view.ui.activity.base.SwipeBackActivity;
import me.itangqi.buildingblocks.domin.utils.ShareUtils;

/*
 * Thanks to
 * Author: drakeet
 */

public class WebActivity extends SwipeBackActivity implements IWebView {

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_ID = "id";
    private SwipeBackLayout mSwipeBackLayout;
    private WebActivityPresenter mPresenter;

    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;
    @Bind(R.id.webView)
    WebView mWebView;

    Context mContext;
    String mUrl;
    int mId;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_webview;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new WebActivityPresenter(this);
        ButterKnife.bind(this);
        mContext = this;
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mId = getIntent().getIntExtra(EXTRA_ID, 0);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (PrefUtils.isEnableCache()) {
            if (NetworkUtils.isNetworkConnected()) {
                webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
            webSettings.setAppCacheEnabled(true);
            webSettings.setDatabaseEnabled(true);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new ViewClient());
        if (PrefUtils.isUsingGson()) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mPresenter.getDailyGson(mId);
        }else {
            mWebView.loadUrl(mUrl);
        }
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_share:
                ShareUtils.share(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) mWebView.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        if (mWebView != null) mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    @Override
    public File getWebViewCacheDir() {
        return this.getCacheDir();
    }

    @Override
    public void loadGsonNews(DailyGson dailyGson) {
        String summery = dailyGson.getBody();
        mWebView.loadData(summery, "text/html; charset=UTF-8", null);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            } else if (newProgress != 100) {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }
    }

    private class ViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }
}
