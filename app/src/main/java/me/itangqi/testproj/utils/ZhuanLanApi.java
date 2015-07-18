package me.itangqi.testproj.utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import me.itangqi.testproj.R;
import me.itangqi.testproj.bean.Post;
import me.itangqi.testproj.bean.User;
import me.itangqi.testproj.data.GsonRequest;


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


    public static GsonRequest getPostListRequest(String id, final String offset) {
        String url = String.format(API_POST_LIST, id);
//                .append("?")
//                .append(KEY_LIMIT + "=" + DEFAULT_COUNT)
//                .append("&")
//                .append(KEY_OFFSET)
//                .append("=")
//                .append(offset).toString();

        return new GsonRequest<List<Post>>(url, buildDefaultErrorListener())
                .addParam(KEY_LIMIT, String.valueOf(DEFAULT_COUNT))
                .addParam(KEY_OFFSET, offset);
    }

    public static GsonRequest<User> getUserInfoRequest(String id) {
        return new GsonRequest<User>(String.format(API_BASE, id), User.class,
                null, buildDefaultErrorListener());
    }


    private static Response.ErrorListener buildDefaultErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showLong(R.string.api_network_error);
            }
        };
    }
}
