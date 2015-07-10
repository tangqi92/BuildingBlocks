package me.itangqi.testproj.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import me.itangqi.testproj.R;
import me.itangqi.testproj.bean.User;
import me.itangqi.testproj.data.RequestManager;
import me.itangqi.testproj.utils.ToastUtils;
import me.itangqi.testproj.utils.Utils;
import me.itangqi.testproj.utils.ZhuanLanApi;
import me.itangqi.testproj.view.CircleImageView;


/**
 * @author bxbxbai
 */
public class PeopleListAdapter extends SimpleBaseAdapter<User> {

    public PeopleListAdapter(Context context, List<User> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.layout_people_info;
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder holder) {
        final User user = getItem(position);

        final CircleImageView imageView = holder.findView(R.id.avatar);

        String id = user.getAuthor().getAvatar().getId();
        String picUrl = Utils.getAuthorAvatarUrl(user.getAuthor().getAvatar().getTemplate(),
                id, ZhuanLanApi.PIC_SIZE_XL);

        imageView.setImageUrl(picUrl, RequestManager.getImageLoader());
//        Picasso.with(mContext).load(picUrl).placeholder(R.drawable.bxbxbai).into(imageView);

        TextView name = holder.findView(R.id.tv_name);
        name.setText(user.getName());

        TextView follower = holder.findView(R.id.tv_follower);
        follower.setText(mContext.getString(R.string.follower, user.getFollowerCount()));

        TextView postCount = holder.findView(R.id.tv_post_count);
        postCount.setText(mContext.getString(R.string.post_count, user.getPostCount()));

        TextView description = holder.findView(R.id.tv_description);
        description.setText(user.getDescription());

        convertView.setTag(R.id.key_slug, user.getSlug());
        convertView.setTag(R.id.key_name, user.getName());

        View v = holder.findView(R.id.ripple_layout);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PostListActivity.start(mContext, user.getSlug(), user.getName());
                ToastUtils.showLong(R.string.toast_test);
            }
        });
    }
}
