package me.itangqi.buildingblocks.domain.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RemoteViews;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.utils.Constants;

/**
 * Created by Troy on 2015/10/2.
 */
public class UpdaterReceiver extends BroadcastReceiver {

    public static final String TAG = "UpdaterReceiver";

    private Notification mNotification = null;
    private NotificationManager mNotificationManager = null;

    private boolean isInitialed = false;
    public static final int ID = 0x123;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isInitialed) {
            init(context);
        }
        int status = intent.getIntExtra(Constants.BROADCAST_STATUS, 0);
        Log.d(TAG, "status--->" + status);
        mNotification.contentView.setProgressBar(R.id.update_progress, 100, status, false);
        mNotificationManager.notify(ID, mNotification);
    }

    private void init(Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification = new Notification.Builder(context)
                .setTicker("正在下载")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("当前下载进度")
                .setWhen(System.currentTimeMillis())
                .build();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.service_update_notification);
        remoteViews.setProgressBar(R.id.update_progress, 100, 0, false);
        mNotification.contentView = remoteViews;
        mNotificationManager.notify(ID, mNotification);
        isInitialed = true;
    }
}
