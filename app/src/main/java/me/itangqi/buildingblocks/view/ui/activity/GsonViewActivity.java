package me.itangqi.buildingblocks.view.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
public class GsonViewActivity extends SwipeBackActivity implements IGsonNews {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_IMG = "img";
    public static final String EXTRA_TITLE = "title";

    public static final int EXTRA = 0x123;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayout mLinearLayout;
    private GsonNewsPresenter mPresenter;
    private ImageView mHeader;
    private ImageView mImageView_avatar;
    private TextView mTextView_author;
    private TextView mTextView_bio;

    private DailyGson mDailyGson;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gson_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        UITask uiTask = new UITask(this);
        uiTask.execute(dailyGson);
//        mPresenter.startInflater(dailyGson);
    }

    @Override
    public Handler getHandler() {
        return null;
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

//    private class UIHandler extends Handler {
//        private final WeakReference<Activity> mActivityWeakReference;
//
//        public UIHandler(Activity activity) {
//            mActivityWeakReference = new WeakReference<Activity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == EXTRA) {
//                Bundle extra = msg.getData();
//                String avatar = extra.getString("avatar");
//                String author = extra.getString("author");
//                String bio = extra.getString("bio");
//                Glide.with(App.getContext()).load(avatar).into(mImageView_avatar);
//                mTextView_author.setText(author);
//                mTextView_bio.setText(bio);
//                mImageView_avatar.setVisibility(View.VISIBLE);
//            }
//        }
//    }

    class UITask extends AsyncTask<DailyGson, Map.Entry<String, String>, Integer> {

        private Context mContext;

        public UITask(Context context) {
            mContext = context;
        }

        @Override
        protected Integer doInBackground(DailyGson... params) {
            Log.d("doInBackground", "params.size--->" + params.length);
            Map<String, LinkedHashMap<String, String>> soup = mPresenter.getContentMap(params[0]);
            Log.d("doInBackground", "soup.size--->" + soup.size());
            LinkedHashMap<String, String> extra = soup.get("extra");
            LinkedHashMap<String, String> article = soup.get("article");
            int index = 0;   // 以后加入进度条后使用
            for (Map.Entry<String, String> entry : extra.entrySet()) {
                publishProgress(entry);
            }
            for (Map.Entry<String, String> entry : article.entrySet()) {
                publishProgress(entry);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Map.Entry<String, String>... values) {
            Map.Entry<String, String> entry = values[0];
            Log.d("refreshUI", entry.getValue() + "--->" + entry.getKey());
            if (entry.getKey().equals("avatar")) {
                Glide.with(App.getContext()).load(entry.getValue()).into(mImageView_avatar);
            } else if (entry.getValue().equals("author")) {
                mTextView_author.setText(entry.getValue());
            } else if (entry.getValue().equals("bio")) {
                mTextView_bio.setText(entry.getValue());
            } else if (entry.getValue().equals("p")) {
                TextView textView = new TextView(mContext);
                textView.setPadding(0, 7, 0, 0);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(15);
                textView.setText(entry.getKey());
                mLinearLayout.addView(textView);
            }else if (entry.getValue().equals("img")) {
                ImageView imageView = new ImageView(App.getContext());
                ViewGroup.LayoutParams ll_params = mLinearLayout.getLayoutParams();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
//                imageView.setPadding(0, 5, 0, 0);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(entry.getKey()).fitCenter().into(imageView);
                mLinearLayout.addView(imageView);
            }else if (entry.getValue().equals("strong")) {

            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        private void loadExtra(LinkedHashMap<String, String> extra) {

        }

        private void loadArticle(LinkedHashMap<String, String> article) {
            for (Map.Entry<String, String> entry : article.entrySet()) {

            }
        }
    }
}
