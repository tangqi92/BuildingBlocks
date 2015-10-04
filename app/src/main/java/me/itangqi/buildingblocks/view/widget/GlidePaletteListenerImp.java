package me.itangqi.buildingblocks.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import me.itangqi.buildingblocks.domain.application.App;

/**
 * Created by Troy on 2015/10/3.
 */
public class GlidePaletteListenerImp implements RequestListener<String, Bitmap> {

    public static final String TAG = "GlidePaletteListener";

    private ImageView mImageView;
    private Activity mActivity;
    private CollapsingToolbarLayout mToolbarLayout;

    public GlidePaletteListenerImp(ImageView imageView, Activity activity, CollapsingToolbarLayout toolbarLayout) {
        mImageView = imageView;
        mActivity = activity;
        mToolbarLayout = toolbarLayout;
    }

    @Override
    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
        Log.d(TAG, "onException--->Target<Bitmap>:isFirstResource->" + isFirstResource);
        return false;
    }

    @Override
    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
        Palette.Builder builder = Palette.from(resource);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch != null) {
                    mToolbarLayout.setBackgroundColor(swatch.getRgb());
                    mToolbarLayout.setContentScrimColor(swatch.getRgb());
                    mToolbarLayout.setCollapsedTitleTextColor(swatch.getTitleTextColor());
                    mToolbarLayout.setStatusBarScrimColor(deeper(swatch.getRgb()));
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        Window window = mActivity.getWindow();
                        window.setStatusBarColor(deeper(swatch.getRgb()));
                        window.setNavigationBarColor(deeper(swatch.getRgb()));
                    }
                }

            }
        });
        return false;
    }

    private int deeper(int rgb) {
        int alpha = rgb >> 24;
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        red = (int) Math.floor(red * (0.9));
        green = (int) Math.floor(green * (0.9));
        blue = (int) Math.floor(blue * (0.9));
        return Color.argb(alpha, red, green, blue);
    }
}
