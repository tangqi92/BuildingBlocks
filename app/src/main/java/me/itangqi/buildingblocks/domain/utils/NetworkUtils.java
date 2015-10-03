package me.itangqi.buildingblocks.domain.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.itangqi.buildingblocks.domain.application.App;

/**
 * Created by tangqi on 9/10/15.
 */
public class NetworkUtils {

    public static boolean isNetworkConnected() {
        Context context = App.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
