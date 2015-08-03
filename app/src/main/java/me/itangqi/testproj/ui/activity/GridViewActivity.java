package me.itangqi.testproj.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import me.itangqi.testproj.R;
import me.itangqi.testproj.kale.photoswall.adapter.PhotoWallAdapter;

/**
 * Created by tangqi on 8/2/15.
 */
public class GridViewActivity extends Activity {


    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;
    private PhotoWallAdapter mAdapter;
    // 小图片的大小
    int mImageThumbSize;
    // 图片间距
    int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mAdapter = new PhotoWallAdapter(this);

        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        mPhotoWall.post(new Runnable() {

            @Override
            public void run() {
                final int numColumns = 4;
//                final int numColumns = (int) Math.floor(mPhotoWall.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                if (numColumns > 0) {
                    int columnWidth = (mPhotoWall.getWidth() / numColumns) - mImageThumbSpacing;
                    mAdapter.setItemHeight(columnWidth);
                }

            }
        });

        mPhotoWall.setAdapter(mAdapter);
    }
}
