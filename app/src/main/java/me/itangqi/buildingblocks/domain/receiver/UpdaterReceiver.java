package me.itangqi.buildingblocks.domain.receiver;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.service.Updater;
import me.itangqi.buildingblocks.domain.utils.IntentKeys;

/**
 * Created by Troy on 2015/10/3.
 * 用来实现，在更新下载失败后的提醒
 */
public class UpdaterReceiver extends BroadcastReceiver {

    public static final String TAG = "UpdaterReceiver";

    private NotificationManager mManager;
    // Dialog的生成需要Activity的Context (需要窗体)
    private Activity mActivity;

    public UpdaterReceiver(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String url = intent.getStringExtra(IntentKeys.URL);
        Log.d(TAG, "url--->" + url);
        mManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mActivity, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(mActivity);
        }
        builder.setTitle("Error");
        builder.setMessage(R.string.service_update_error);
        builder.setNegativeButton(R.string.service_update_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mManager.cancel(Updater.ID);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.service_update_restart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent service = new Intent(context, Updater.class);
                service.putExtra(IntentKeys.URL, url);
                context.startService(service);
            }
        });
        builder.create().show();
    }
}
