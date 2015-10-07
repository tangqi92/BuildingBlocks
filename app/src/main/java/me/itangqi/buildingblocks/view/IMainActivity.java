package me.itangqi.buildingblocks.view;

import android.net.Uri;

import java.util.List;

/**
 * Created by Troy on 2015/9/26.
 */
public interface IMainActivity {

    void showSnackBar(String data, int time);

    void showSnackBarWithAction(String data, int time, Uri uri);

    void showUpdate(int versionCode, String versionName, String apkUrl, List<String> disc);
}
