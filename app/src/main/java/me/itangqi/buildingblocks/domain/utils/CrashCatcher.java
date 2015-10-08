package me.itangqi.buildingblocks.domain.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.application.App;

/**
 * 进行错误收集，并由用户选择是否发送回来
 * Created by Troy on 2015/10/7.
 */
public class CrashCatcher implements Thread.UncaughtExceptionHandler {

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
        ex.printStackTrace();
        Uri uri = saveToSDCard(ex);
        PrefUtils.setCrash(true, uri.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showLong(R.string.crash_toast);
                Looper.loop();
            }
        }).start();
        try {
            Thread.sleep(3000);
            App.exitAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        List<String> info = DeviceUtils.getDeviceMsg(mContext);
        for (String s : info) {
            buffer.append(s).append("\n");
        }
        buffer.append("=====\tError Log\t=====\n");
        String errorMsgs = catchErrors(throwable);
        buffer.append(errorMsgs);
        File dir = new File(Constants.LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File crash = new File(dir, Constants.LOG_NAME);
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
