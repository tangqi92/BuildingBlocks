package me.itangqi.buildingblocks.view.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.presenters.GsonNewsPresenter;
import me.itangqi.buildingblocks.view.IGsonNews;
import me.itangqi.buildingblocks.view.ui.activity.base.SwipeBackActivity;

/**
 * Created by Troy on 2015/9/24.
 */
public class GsonViewActivity extends SwipeBackActivity implements IGsonNews{

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_IMG = "img";
    public static final String EXTRA_TITLE = "title";

    public static final int EXTRA = 0x123;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayout mLinearLayout;
    private UIHandler mHandler;
    private GsonNewsPresenter mPresenter;
    private ImageView mHeader;
    private static ImageView mImageView_avatar;
    private static TextView mTextView_author;
    private static TextView mTextView_bio;

    private DailyGson mDailyGson;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gson_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new UIHandler(this);
        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        mPresenter = new GsonNewsPresenter(this);
        initView(title);
        mPresenter.getGsonNews(id);
    }

    @Override
    public void loadGson(DailyGson dailyGson) {
        mDailyGson = dailyGson;
        Glide.with(this).load(dailyGson.getImage()).fitCenter().into(mHeader);
        mPresenter.startInflater(dailyGson);
//        UITask uiTask = new UITask();
//        uiTask.execute(dailyGson);
        mPresenter.startInflater(dailyGson);
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void initView(String title) {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_content);
        mHeader = (ImageView) findViewById(R.id.news_header);
        mImageView_avatar = (ImageView) findViewById(R.id.iv_avatar);
        mTextView_author = (TextView) findViewById(R.id.tv_author);
        mTextView_bio = (TextView) findViewById(R.id.tv_bio);
        mCollapsingToolbarLayout.setTitle(title);
    }

    private class UIHandler extends Handler {
        private final WeakReference<Activity> mActivityWeakReference;

        public UIHandler(Activity activity) {
            mActivityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == EXTRA) {
                Bundle extra = msg.getData();
                String avatar = extra.getString("avatar");
                String author = extra.getString("author");
                String bio = extra.getString("bio");
                Glide.with(App.getContext()).load(avatar).into(mImageView_avatar);
                mTextView_author.setText(author);
                mTextView_bio.setText(bio);
                mImageView_avatar.setVisibility(View.VISIBLE);
            }
        }
    }

//     class UITask extends AsyncTask<DailyGson,String,Map> {
//
//         @Override
//         protected Map doInBackground(DailyGson... params) {
//             Log.d("doInBackground", "params.size--->" + params.length);
//             Map<String, LinkedHashMap<String, String>> soup = mPresenter.getContentMap(params[0]);
//             Log.d("doInBackground", "soup.size--->" + soup.size());
//             LinkedHashMap<String, String> extra = soup.get("extra");
//             LinkedHashMap<String, String> article = soup.get("article");
//             int index = 0;
//             for (Map.Entry<String, String> entry : extra.entrySet()) {
//                 publishProgress(entry);
//             }
//             return null;
//         }
//
//         @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//         @Override
//         protected void onProgressUpdate(String... values) {
//             Log.d("refreshUI", entry.getValue() + "--->" + entry.getKey());
//             if (entry.getValue().equals("avatar")) {
//                 Glide.with(App.getContext()).load(entry.getKey()).into(mImageView_avatar);
//             }
//             if (entry.getValue().equals("author")) {
//                 mTextView_author.setText(entry.getKey());
//             }
//             if (entry.getValue().equals("bio")) {
//                 mTextView_bio.setText(entry.getKey());
//             }
//         }
//
//         @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        private void loadExtra(LinkedHashMap<String, String> extra) {
//
//        }
//
//        private void loadArticle(LinkedHashMap<String, String> article) {
//            for (Map.Entry<String, String> entry : article.entrySet()) {
//
//            }
//        }
//    }
}
