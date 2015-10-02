package me.itangqi.buildingblocks.domain.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import me.itangqi.buildingblocks.R;

/**
 * Created by Troy on 2015/10/2.
 */
public class Updater extends IntentService {

    public static final String TAG = "Updater";

    private int hasDown;
    private int size;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    public static final int ID = 0x123;

    public Updater() {
        super("Updater");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification = new Notification.Builder(this)
                .setTicker("正在下载")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("当前下载进度")
                .setWhen(System.currentTimeMillis())
                .build();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.service_update_notification);
        remoteViews.setProgressBar(R.id.update_progress, 100, 0, false);
        mNotification.contentView = remoteViews;
        mNotificationManager.notify(ID, mNotification);
        String urlStr = intent.getStringExtra("url");
        String name = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
        Uri uri = null;
        File apk = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                size = connection.getContentLength();
                is = connection.getInputStream();
                bis = new BufferedInputStream(is);
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                apk = new File(dir, name);
                fos = new FileOutputStream(apk);
                bos = new BufferedOutputStream(fos);
                byte[] buffer = new byte[1024];
                int hasRead;
                showStatus();
                while ((hasRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, hasRead);
                    hasDown += hasRead;
                }
                uri = Uri.fromFile(apk);
                installApk(uri);
            } else {
                Log.d(TAG, "responseCode--->" + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showStatus() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int status = hasDown * 100 / size;
                mNotification.contentView.setProgressBar(R.id.update_progress, 100, status, false);
                mNotificationManager.notify(ID, mNotification);
                if (status >= 100) {
                    mNotificationManager.cancel(ID);
                    cancel();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 100);
    }

    private void installApk(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
