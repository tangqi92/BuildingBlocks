package me.itangqi.buildingblocks.view;

import java.util.List;

/**
 * Created by Troy on 2015/9/26.
 */
public interface IMainActivity {

    void showSnackBar(String data, int time);

    void showUpdate(int versionCode, String versionName, String apkUrl, List<String> disc);
}
