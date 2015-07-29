package me.itangqi.testproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import me.itangqi.testproj.R;
import me.itangqi.testproj.bean.User;
import me.itangqi.testproj.data.RequestManager;
import me.itangqi.testproj.utils.Utils;
import me.itangqi.testproj.utils.ZhuanLanApi;
import me.itangqi.testproj.view.CircleImageView;

/**
 * Created by tangqi on 7/11/15.
 */
public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.CardViewHolder> {
    private List<User> mDatas;
    private Context mContext;

    public PeopleListAdapter(List<User> mDatas) {
        this.mDatas = mDatas;
        setHasStableIds(true);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_people_info, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(itemView, new CardViewHolder.IMyViewHolderClicks() {
            @Override
            public void onPotato(View caller, int position) {
                Logger.d("Poh-tah-tos" + position);
            }
        });
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        User user = mDatas.get(position);
        String id = user.getAuthor().getAvatar().getId();
        String picUrl = Utils.getAuthorAvatarUrl(user.getAuthor().getAvatar().getTemplate(),
                id, ZhuanLanApi.PIC_SIZE_XL);
        holder.imageView.setImageUrl(picUrl, RequestManager.getImageLoader());
        holder.name.setText(user.getName());
        holder.follower.setText(mContext.getString(R.string.people_list_follower, user.getFollowerCount()));
        holder.postCount.setText(mContext.getString(R.string.people_list_post_count, user.getPostCount()));
        holder.description.setText(user.getDescription());
    }

    public void add(User user) {
        mDatas.add(user);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView imageView;
        public TextView name;
        public TextView follower;
        public TextView postCount;
        public TextView description;
        public IMyViewHolderClicks mListener;

        public CardViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            mListener = listener;
            imageView = (CircleImageView) v.findViewById(R.id.avatar);
            name = (TextView) v.findViewById(R.id.tv_name);
            follower = (TextView) v.findViewById(R.id.tv_follower);
            postCount = (TextView) v.findViewById(R.id.tv_post_count);
            description = (TextView) v.findViewById(R.id.tv_description);
            v.setOnClickListener(this);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onPotato(view, getPosition());
        }

        interface IMyViewHolderClicks {
            void onPotato(View caller, int position);
        }
    }
}
