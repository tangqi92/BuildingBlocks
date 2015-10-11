package me.itangqi.buildingblocks.view.ui.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedHashMap;
import java.util.Map;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.application.App;
import me.itangqi.buildingblocks.domain.utils.IntentKeys;
import me.itangqi.buildingblocks.domain.utils.PrefUtils;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.presenters.GsonNewsPresenter;
import me.itangqi.buildingblocks.view.IGsonNews;
import me.itangqi.buildingblocks.view.ui.activity.base.SwipeBackActivity;
import me.itangqi.buildingblocks.view.widget.GlidePaletteListenerImp;

/**
 * Created by Troy on 2015/9/24.
 */
public class GsonViewActivity extends SwipeBackActivity implements IGsonNews {

    public static final int EXTRA = 0x123;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayout mLinearLayout;
    private GsonNewsPresenter mPresenter;
    private ImageView mHeader;
    private WebView mWebView;

    private GlidePaletteListenerImp mPaletteListenerImp;

    private DailyGson mDailyGson;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gson_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
        int id = getIntent().getIntExtra(IntentKeys.EXTRA_ID, 0);
        String title = getIntent().getStringExtra(IntentKeys.EXTRA_TITLE);
        mPresenter = new GsonNewsPresenter(this);
        initView(title);
        mPaletteListenerImp = new GlidePaletteListenerImp(mHeader, this, mCollapsingToolbarLayout);
        mPresenter.getGsonNews(id);
    }

    @Override
    public void loadGson(DailyGson dailyGson) {
        mDailyGson = dailyGson;
        Glide.with(App.getContext()).load(dailyGson.image).asBitmap().fitCenter().listener(mPaletteListenerImp).into(mHeader);
        String head = "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                "<title>" + dailyGson.title + "</title>\n" +
                "<meta name=\"viewport\" content=\"user-scalable=no, width=device-width\">\n" +
                "<link rel=\"stylesheet\" href=\"" + "http://7xk54v.com1.z0.glb.clouddn.com/app/bb/css/zhihu.css" + "\">\n" +
                "<style type=\"text/css\"></style>\n" +
                "<base target=\"_blank\">\n" +
                "</head>";
        String bodyStart = "<body>";
        String bodyEnd = "</body>";
        mWebView.loadData(head + bodyStart + dailyGson.body.replaceAll("<div class=\"img-place-holder\"></div>", "") + bodyEnd, "text/html; charset=uft-8", "utf-8");
    }

    @Override
    public Handler getHandler() {
        return null;
    }

    private void initView(String title) {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_content);
        mHeader = (ImageView) findViewById(R.id.news_header);
        mWebView = (WebView) findViewById(R.id.webView);
        mCollapsingToolbarLayout.setTitle(title);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (PrefUtils.isEnableCache()) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDatabaseEnabled(true);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
}
