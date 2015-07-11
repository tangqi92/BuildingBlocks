package me.itangqi.testproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.itangqi.testproj.R;
import me.itangqi.testproj.bean.DailyNews;

/**
 * Created by tangqi on 7/11/15.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.CardViewHolder> {
    private List<DailyNews> mDatas;

    public NewsListAdapter(List<DailyNews> mDatas) {
        this.mDatas = mDatas;
        setHasStableIds(true);

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        View itemView = LayoutInflater
                .from(context)
                .inflate(R.layout.news_list_item, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(itemView);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
         DailyNews dailyNews = mDatas.get(position);
        holder.dailyTitle.setText(dailyNews.getDailyTitle());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView newsImage;
        public TextView questionTitle;
        public TextView dailyTitle;
        public ImageView overflow;


        public CardViewHolder(View v) {
            super(v);
            newsImage = (ImageView) v.findViewById(R.id.thumbnail_image);
            questionTitle = (TextView) v.findViewById(R.id.question_title);
            dailyTitle = (TextView) v.findViewById(R.id.daily_title);
            overflow = (ImageView) v.findViewById(R.id.card_share_overflow);

        }


    }
}
