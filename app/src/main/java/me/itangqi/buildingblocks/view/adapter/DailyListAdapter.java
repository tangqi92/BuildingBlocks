package me.itangqi.buildingblocks.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.api.ZhihuApi;
import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.view.ui.activity.WebActivity;

/**
 * Created by tangqi on 8/20/15.
 */
public class DailyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Daily> mNewsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_TEXT = 2;

    public DailyListAdapter(Context mContext, List<Daily> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
        mLayoutInflater = LayoutInflater.from(mContext);
        // I hate it !!!
        // http://stackoverflow.com/questions/28787008/onbindviewholder-position-is-starting-again-at-0
        // setHasStableIds(true);
        Log.d("mNewsList's Size: ", mNewsList.size() + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_IMAGE) {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_daily_image_info, parent, false));
        } else {
            return new ThemeViewHolder(mLayoutInflater.inflate(R.layout.item_daily_text_info, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Daily news = mNewsList.get(position);
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_TEXT:
                ((ThemeViewHolder) holder).mTitle.setText(news.title);
                ((ThemeViewHolder) holder).mFrom.setText("From " + news.theme.name);
                break;
            case ITEM_TYPE_IMAGE:
                ((ImageViewHolder) holder).mTitle.setText(news.title);
                Glide.with(mContext).load(news.images.get(0)).into(((ImageViewHolder) holder).mCover);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // add here your booleans or switch() to set viewType at your needed
        Daily news = mNewsList.get(position);
        // Depending on whether the presence of images to determine the type of load View
        return (news.images == null || news.images.size() == 0) ? ITEM_TYPE_TEXT : ITEM_TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void gotoWebView(Daily news, View v) {
        String news_url = ZhihuApi.getNewsContent(news.id);
        Intent intent = new Intent(v.getContext(), WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URL, news_url);
        v.getContext().startActivity(intent);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_cover) ImageView mCover;
        @Bind(R.id.tv_title) TextView mTitle;

        public ImageViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.ll_card_parent)
        void onClick(View v) {
            // TODO do what you want :) you can use WebActivity to load
            Daily news = mNewsList.get(getLayoutPosition());
            gotoWebView(news, v);
        }
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title) TextView mTitle;
        @Bind(R.id.tv_from) TextView mFrom;

        public ThemeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.ll_theme_parent)
        void onClick(View v) {
            // TODO do what you want :) you can use WebActivity to load
            Daily news = mNewsList.get(getLayoutPosition());
            gotoWebView(news, v);
        }
    }
}
