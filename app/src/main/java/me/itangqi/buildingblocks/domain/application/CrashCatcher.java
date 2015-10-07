package me.itangqi.buildingblocks.domain.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 进行错误收集，并由用户选择是否发送回来
 * Created by Troy on 2015/10/7.
 */
public class CrashCatcher implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashCatcher";

    private static CrashCatcher crashCatcher;
    private Context mContext;

    private CrashCatcher() {
    }

    public static CrashCatcher newInstance() {
        if (crashCatcher != null) {
            return crashCatcher;
        } else {
            return new CrashCatcher();
        }
    }

    public void setDefaultCrashCatcher() {
        mContext = App.getContext();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Uri uri = saveToSDCard(ex);
        SharedPreferences.Editor editor = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isLastTimeCrashed", true);
        editor.putString("crashUri", uri.toString());
        editor.commit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "我好像坏掉了，但是我记下了细节≧︿≦", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
        try {
            Thread.sleep(3500);
            App.exit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<String> getDeviceMsg() {
        List<String> deviceInfo = new ArrayList<>();
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            deviceInfo.add("=====\tDevice Info\t=====");
            deviceInfo.add("manufacture:" + Build.MANUFACTURER);
            deviceInfo.add("product:" + Build.PRODUCT);
            deviceInfo.add("model:" + Build.MODEL);
            deviceInfo.add("version.release:" + Build.VERSION.RELEASE);
            deviceInfo.add("version:"+ Build.DISPLAY);
            deviceInfo.add("=====\tBB Info\t=====");
            deviceInfo.add("versionCode:" + info.versionCode + "");
            deviceInfo.add("versionName:" + info.versionName);
            deviceInfo.add("lastUpdateTime:" + new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒", Locale.CHINA).format(info.lastUpdateTime));

        }
        return deviceInfo;
    }

    private String catchErrors(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    private Uri saveToSDCard(Throwable throwable) {
        StringBuilder buffer = new StringBuilder();
        List<String> info = getDeviceMsg();
        for (String s : info) {
            buffer.append(s).append("\n");
        }
        buffer.append("=====\tError Log\t=====\n");
        String errorMsgs = catchErrors(throwable);
        buffer.append(errorMsgs);
        File dir = new File(mContext.getExternalCacheDir(), "log");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File crash = new File(dir, "crash.log");
        try {
            FileOutputStream fos = new FileOutputStream(crash);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(buffer.toString());
            osw.flush();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(crash);
    }
}
