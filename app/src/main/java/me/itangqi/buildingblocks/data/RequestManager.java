package me.itangqi.buildingblocks.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import me.itangqi.buildingblocks.application.App;


/**
 * 维护一个全局的Request队列，添加Request或者取消Request
 *
 * Created by baia on 14-6-5.
 */
public final class RequestManager {

    private static final RequestQueue mRequestQueue = Volley.newRequestQueue(App.getInstance());

    private static ImageLoader imageLoader;

    private RequestManager(){

    }

    public static void addRequest(Request<?> request, Object tag){
        if(tag != null) {
            request.setTag(tag.hashCode());
        }
        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag ){
        mRequestQueue.cancelAll(tag);
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(getRequestQueue(), new BitmapLruCache());
        }
        return imageLoader;
    }
}
