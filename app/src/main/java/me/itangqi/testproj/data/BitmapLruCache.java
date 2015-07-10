package me.itangqi.testproj.data;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 实现一个简单的在内存中的图片LruCache，创建一个Cache 的时候需要设定这个Cache 的最大容量
 * 父类LruCache会根据这个大小去创建一个LinkedHashMap。<br/>
 * 这个LruCache也实现了ImageCache接口，在使用ImageLoader的时候，可以使用这个Cache去缓存图片。
 *
 * @author bxbxbai
 *
 * Created by baia on 14-6-4.
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    /**
     * 创建一个BitmapLruCache
     * @param maxSize
     */
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    public BitmapLruCache() {
        this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return value.getByteCount();
        }
        //pre HONEYCOMB_MR1
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
