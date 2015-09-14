package me.itangqi.buildingblocks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import me.itangqi.buildingblocks.model.Daily;
import me.itangqi.buildingblocks.ui.activity.WebActivity;

/**
 * Created by tangqi on 8/20/15.
 */
public class DailyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }
    private List<Daily> mNewsList;
    private Context mContext;

    public DailyListAdapter(Context mContext, List<Daily> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
        // I hate it !!!
        // http://stackoverflow.com/questions/28787008/onbindviewholder-position-is-starting-again-at-0
//        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal()) {
            return new ImageViewHolder(LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.item_daily_normal_info, parent, false));
        } else {
            return new ThemeViewHolder(LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.item_daily_theme_info, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Daily news = mNewsList.get(position);
        if (holder instanceof ThemeViewHolder) {
            ((ThemeViewHolder) holder).mTitle.setText(news.title);
            ((ThemeViewHolder) holder).mFrom.setText(news.theme.name);
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).mTitle.setText(news.title);
            Glide.with(mContext).load(news.images.get(0)).into(((ImageViewHolder)holder).mCover);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Daily news = mNewsList.get(position);
        if (news.images == null || news.images.size() == 0) {
             return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
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
            // TODO do what you want :) you can use WebActivity to load detail content
            Daily news = mNewsList.get(getLayoutPosition());
            String news_url = ZhihuApi.getNewsContent(news.id);
            Intent intent = new Intent(v.getContext(), WebActivity.class);
            intent.putExtra(WebActivity.EXTRA_TITLE, news.title);
            intent.putExtra(WebActivity.EXTRA_URL, news_url);
            v.getContext().startActivity(intent);
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
            // TODO do what you want :) you can use WebActivity to load detail content
            Daily news = mNewsList.get(getLayoutPosition());
            String news_url = ZhihuApi.getNewsContent(news.id);
            Intent intent = new Intent(v.getContext(), WebActivity.class);
            intent.putExtra(WebActivity.EXTRA_TITLE, news.title);
            intent.putExtra(WebActivity.EXTRA_URL, news_url);
            v.getContext().startActivity(intent);
        }
    }
}
