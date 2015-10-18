package me.itangqi.buildingblocks.domain.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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
import me.itangqi.buildingblocks.domain.utils.Constants;
import me.itangqi.buildingblocks.domain.utils.IntentKeys;

/**
 * Created by Troy on 2015/10/2.
 * 用来实现下载功能，根据Pref的设置，是否每次在MainActivity启动时调用
 */
public class Updater extends IntentService {

    public static final String TAG = "Updater";

    private String urlStr = null;
    private int hasDown;
    private int size;
    private boolean isError = false;

    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder;
    public static final int ID = 0x123;

    public Updater() {
        super("Updater");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        mNotificationBuilder = new Notification.Builder(this)
                .setContentTitle(getString(R.string.service_updating))
                .setProgress(100, 0, false)
                .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon)
                .setVibrate(new long[]{500})
                .setOngoing(true);
        urlStr = intent.getStringExtra(IntentKeys.URL);
        Log.d(TAG, "url--->" + urlStr);
        download();
    }

    private void download() {
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
                File dir = new File(Constants.DOWNLOAD_DIR);
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
            isError = true;
            showError();
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
                int status = (int) (((float) hasDown / size) * 100);
                Log.d(TAG, "size--->" + size + "; hasDown--->" + hasDown);
                Log.d(TAG, "status--->" + status);
                if (!isError) {
                    mNotificationManager.notify(ID, mNotificationBuilder.setProgress(100, status, false).build());
                } else {
                    mNotificationManager.cancel(ID);
                    cancel();
                }
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
        this.stopSelf();
    }

    private void showError() {
        Intent intent = new Intent(Constants.BROADCAST_UPDATE_ACTION);
        intent.addCategory(Constants.BROADCAST_UPDATE_CATEGORY);
        intent.putExtra(IntentKeys.URL, urlStr);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        this.stopSelf();
    }
}
