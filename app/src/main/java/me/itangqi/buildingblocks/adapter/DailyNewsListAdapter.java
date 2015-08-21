package me.itangqi.buildingblocks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.model.DailyNews;
import me.itangqi.buildingblocks.utils.RequestManager;

/**
 * Created by tangqi on 8/20/15.
 */
public class DailyNewsListAdapter extends RecyclerView.Adapter<DailyNewsListAdapter.CardViewHolder> {
    private List<DailyNews> mNewsList;
    private Context mContext;

    public DailyNewsListAdapter(List<DailyNews> mNewsList) {
        this.mNewsList = mNewsList;
        setHasStableIds(true);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_news_info, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(itemView);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        DailyNews news = mNewsList.get(position);
        // 目前暂未有使用多张图片的情形出现
        holder.mCover.setImageUrl(news.images.get(0), RequestManager.getImageLoader());
        holder.mTitle.setText(news.title);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_cover) NetworkImageView mCover;
        @Bind(R.id.tv_title) TextView mTitle;

        public CardViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.ll_card_parent)
        void onClick(View v) {
            // TODO do what you want :) you can use WebActivity
        }
    }
}
