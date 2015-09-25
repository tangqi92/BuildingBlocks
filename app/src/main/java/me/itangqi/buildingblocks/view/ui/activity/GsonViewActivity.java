package me.itangqi.buildingblocks.view.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.model.IHttpCallBack;
import me.itangqi.buildingblocks.model.entity.Daily;
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
    private TextView mTextView_content;

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
        LinkedHashMap<String, String> extra = mPresenter.getContentMap(dailyGson).get("extra");
        for (Map.Entry<String, String> entry : extra.entrySet()) {
            if (entry.getKey().equals("avatar")) {
                Glide.with(this).load(entry.getValue()).into(mImageView_avatar);
            }else if (entry.getKey().equals("author")) {
                mTextView_author.setText(entry.getValue());
            }else if (entry.getKey().equals("bio")) {
                mTextView_bio.setText(entry.getValue());
            }else if (entry.getKey().equals("allcontent")) {
                String content = entry.getValue();
                mTextView_content.setMovementMethod(ScrollingMovementMethod.getInstance());
                mTextView_content.setText(Html.fromHtml(content, new ImageGetter(this, mTextView_content), null));
            }
        }
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
        mTextView_content = (TextView) findViewById(R.id.tv_content);
        mCollapsingToolbarLayout.setTitle(title);
    }

    private class ImageGetter implements Html.ImageGetter {

        private Context mContext;
        private TextView mTextView;

        public ImageGetter(Context context, TextView textView) {
            mContext = context;
            mTextView = textView;
        }

        @Override
        public Drawable getDrawable(String source) {
            ImageDrawable imageDrawable = new ImageDrawable(mContext);
            ImageTasker tasker = new ImageTasker(imageDrawable);
            tasker.execute(source);
            return imageDrawable;
        }

        private class ImageTasker extends AsyncTask<String, Void, Drawable> {

            private ImageDrawable mDrawable;

            public ImageTasker(ImageDrawable drawable) {
                mDrawable = drawable;
            }

            @Override
            protected Drawable doInBackground(String... params) {
                URL url = null;
                Drawable drawable = null;
                try {
                    url = new URL(params[0]);
                    drawable = Drawable.createFromStream(url.openStream(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                if (drawable != null) {
                    mDrawable.mDrawable = drawable;
                    ImageGetter.this.mTextView.requestLayout();
                }
            }

        }

        private class ImageDrawable extends BitmapDrawable {

            protected Drawable mDrawable;

            public ImageDrawable(Context context) {
                this.setBounds(getDefaultImageBounds(context));
                mDrawable = context.getResources().getDrawable(R.drawable.icon);
                mDrawable.setBounds(getDefaultImageBounds(context));
            }

            @Override
            public void draw(Canvas canvas) {
                if (mDrawable != null) {
                    mDrawable.draw(canvas);
                }
            }

            public Rect getDefaultImageBounds(Context context) {
                Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();
                Rect bounds = new Rect(0, 0, width, height);
                return bounds;
            }
        }
    }

}
