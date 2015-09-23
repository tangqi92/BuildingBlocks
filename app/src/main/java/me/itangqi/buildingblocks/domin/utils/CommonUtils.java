package me.itangqi.buildingblocks.domin.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.application.App;

public class CommonUtils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static void writeLog(String name, String data) {
        String cacheDir = App.getContext().getCacheDir().getAbsolutePath();
        File logDir = new File(cacheDir + "/log");
        if (!logDir.exists()) {
            Log.d("logDir", logDir.mkdir() ? "logDir创建成功" : "创建失败");
        }
        File log = new File(logDir, name);
        try {
            if (log.exists()) {
                log.delete();
                log.createNewFile();
            } else {
                log.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(log));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
