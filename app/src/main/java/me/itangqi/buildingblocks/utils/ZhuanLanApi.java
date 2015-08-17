package me.itangqi.buildingblocks.utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;

import me.itangqi.buildingblocks.bean.User;
import me.itangqi.buildingblocks.data.GsonRequest;


/**
 *
 * @author bxbxbai
 */
public final class ZhuanLanApi {

    public static final int DEFAULT_COUNT = 10;

    private static final String KEY_POSTS = "/posts";
    private static final String KEY_LIMIT = "limit";
    private static final String KEY_OFFSET = "offset";
    private static final String KEY_RATING = "rating";

    public static final String ZHUAN_LAN_URL = "http://zhuanlan.zhihu.com";
    private static final String API_BASE = ZHUAN_LAN_URL + "/api/columns/%s";
    /**
     * 知乎日报启动画面api（手机分辨率的长和宽）
     */
    private static final String API_START_IMAGE = "http://news-at.zhihu.com/api/4/start-image/%d*%d";
    private static final String API_RATING = API_BASE + KEY_POSTS + "{post_id}" + KEY_RATING;
    private static final String API_POST_LIST = API_BASE + KEY_POSTS;


    public static final String PIC_SIZE_XL = "xl";
    public static final String PIC_SIZE_XS = "xs";


    public static final String TEMPLATE_ID = "{id}";
    public static final String TEMPLATE_SIZE = "{size}";

    public static GsonRequest<User> getUserInfoRequest(String id) {
        return new GsonRequest<User>(String.format(API_BASE, id), User.class,
                null, buildDefaultErrorListener());
    }

    private static Response.ErrorListener buildDefaultErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e(error, "error");
            }
        };
    }

    public static String getAuthorAvatarUrl(String origin, String userId, String size) {
        origin = origin.replace(ZhuanLanApi.TEMPLATE_ID, userId);
        return origin.replace(ZhuanLanApi.TEMPLATE_SIZE, size);
    }
}
