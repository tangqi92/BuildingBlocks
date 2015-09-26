package me.itangqi.buildingblocks.view.ui.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

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
        Glide.with(App.getContext()).load(dailyGson.getImage()).fitCenter().into(mHeader);
        UITask task = new UITask();
        task.execute(dailyGson);
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

    private class UITask extends AsyncTask<DailyGson, Map.Entry<String, String>, Integer> {

        @Override
        protected Integer doInBackground(DailyGson... params) {
            Map<String, LinkedHashMap<String, String>> soup = mPresenter.getContentMap(params[0]);
            LinkedHashMap<String, String> extra = soup.get("extra");
            LinkedHashMap<String, String> article = soup.get("article");
            for (Map.Entry<String, String> entry : extra.entrySet()) {
                publishProgress(entry);
            }
            for (Map.Entry<String, String> entry : article.entrySet()) {
                publishProgress(entry);
            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Map.Entry<String, String>... values) {
            Map.Entry<String, String> entry = values[0];
            if (entry.getKey().equals("avatar")) {
                Glide.with(App.getContext()).load(entry.getValue()).into(mImageView_avatar);
            } else if (entry.getKey().equals("author")) {
                mTextView_author.setText(entry.getValue());
            } else if (entry.getKey().equals("bio")) {
                mTextView_bio.setText(entry.getValue());
            } else if (entry.getValue().equals("p")) {
                TextView textView = new TextView(App.getContext());
                textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(17);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                textView.setText(Html.fromHtml(entry.getKey()));
                mLinearLayout.addView(textView);
            }else if (entry.getValue().equals("img")) {
                ImageView imageView = new ImageView(App.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mLinearLayout.addView(imageView);
                Glide.with(App.getContext()).load(entry.getKey()).crossFade().fitCenter().into(imageView);
            }else if (entry.getValue().equals("simpleBoldP")) {
                TextView textView = new TextView(App.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                textView.setTextSize(17);
                textView.getPaint().setFakeBoldText(true);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.START);
                textView.setText(entry.getKey());
                mLinearLayout.addView(textView);
            }else if (entry.getValue().equals("simpleP")) {
                TextView textView = new TextView(App.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                textView.setTextSize(17);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.START);
                textView.setText(entry.getKey());
                mLinearLayout.addView(textView);
            }
        }
    }

}
