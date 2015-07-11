package me.itangqi.testproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.CardViewHolder> {
    private List<User> mDatas;

    public NewsListAdapter(List<User> mDatas) {
        this.mDatas = mDatas;
        setHasStableIds(true);

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        View itemView = LayoutInflater
                .from(context)
                .inflate(R.layout.layout_people_info, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(itemView);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        User user = mDatas.get(position);

        String id = user.getAuthor().getAvatar().getId();
        String picUrl = Utils.getAuthorAvatarUrl(user.getAuthor().getAvatar().getTemplate(),
                id, ZhuanLanApi.PIC_SIZE_XL);

        holder.imageView.setImageUrl(picUrl, RequestManager.getImageLoader());
//        Picasso.with(mContext).load(picUrl).placeholder(R.drawable.bxbxbai).into(imageView);

        holder.name.setText(user.getName());

//        holder.follower.setText(user.getFollowerCount());
//
//        holder.postCount.setText(user.getPostCount());

        holder.description.setText(user.getDescription());

//        convertView.setTag(R.id.key_slug, user.getSlug());
//        convertView.setTag(R.id.key_name, user.getName());


    }

    public void add(User user) {
        mDatas.add(user);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView name;
        public TextView follower;
        public TextView postCount;
        public TextView description;


        public CardViewHolder(View v) {
            super(v);
            imageView = (CircleImageView) v.findViewById(R.id.avatar);
            name = (TextView) v.findViewById(R.id.tv_name);
            follower = (TextView) v.findViewById(R.id.tv_follower);
            postCount = (TextView) v.findViewById(R.id.tv_post_count);
            description = (TextView) v.findViewById(R.id.tv_description);

        }


    }
}
