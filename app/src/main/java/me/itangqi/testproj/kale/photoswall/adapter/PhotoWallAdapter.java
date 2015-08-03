package me.itangqi.testproj.kale.photoswall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import me.itangqi.testproj.R;
import me.itangqi.testproj.kale.photoswall.provider.Images;
import me.itangqi.testproj.kale.photoswall.util.LruBitmapCache;
import me.itangqi.testproj.kale.photoswall.util.ViewHolder;
import me.itangqi.testproj.utils.App;

public class PhotoWallAdapter extends BaseAdapter implements OnScrollListener {

    private Context mContext;
    private static ImageLoader mImageLoader; // imageLoader对象，用来初始化NetworkImageView
    /**
     * 记录每个子项的高度。
     */
    private int mItemHeight = 0;


    public PhotoWallAdapter(Context context) {
        mContext = context;
        // 初始化mImageLoader，并且传入了自定义的内存缓存
        mImageLoader = new ImageLoader(App.requestQueue, new LruBitmapCache()); // 初始化一个loader对象，可以进行自定义配置
        // 配置是否进行磁盘缓存
//        mImageLoader.setShouldCache(true); // 设置允许磁盘缓存，默认是true
    }

    @Override
    public int getCount() {
        return Images.imageThumbUrls.length; // 返回item的个数
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * 重要的方法。通过viewHolder复用view，并且设置好item的宽度
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // init convertView by layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.photo_layout, null);
        }
        // 得到item中显示图片的view
        NetworkImageView networkImageView = ViewHolder.get(convertView, R.id.netork_imageView);

        // 设置默认的图片
        networkImageView.setDefaultImageResId(R.drawable.default_photo);
        // 设置图片加载失败后显示的图片
        networkImageView.setErrorImageResId(R.drawable.error_photo);

        if (networkImageView.getLayoutParams().height != mItemHeight) {
            // 通过activity中计算出的结果，在这里设置networkImageview的高度（得到的是正方形）
            networkImageView.getLayoutParams().height = mItemHeight;
        }

        // 开始加载网络图片
        networkImageView.setImageUrl(Images.imageThumbUrls[position], mImageLoader);
        return convertView;
    }

    /**
     * 设置item子项的高度。
     */
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        notifyDataSetChanged();
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
        if (scrollState == SCROLL_STATE_IDLE) {
            // loadBitmaps(mFirstVisibleItem, mVisibleItemCount);  
        } else {
            // cancelAllTasks();  
        }
    }


}
