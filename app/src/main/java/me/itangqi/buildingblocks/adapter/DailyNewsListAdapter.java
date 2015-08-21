package me.itangqi.buildingblocks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.model.DailyNews;

/**
 * Created by tangqi on 8/20/15.
 */
public class DailyNewsListAdapter extends RecyclerView.Adapter<DailyNewsListAdapter.CardViewHolder> {
    private List<DailyNews> mNewsList;
    private Context mContext;

    public DailyNewsListAdapter(Context mContext, List<DailyNews> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
        // I hate it !!!
        // http://stackoverflow.com/questions/28787008/onbindviewholder-position-is-starting-again-at-0
//        setHasStableIds(true);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_news_info, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(itemView);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        DailyNews news = mNewsList.get(position);
        Logger.d(position+"");
        // 目前暂未有使用多张图片的情形出现
        Glide.with(mContext).load(news.images.get(0)).into(holder.mCover);
        holder.mTitle.setText(news.title);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_cover) ImageView mCover;
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
