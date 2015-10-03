package me.itangqi.buildingblocks.domain.utils;

import android.content.Context;
import android.content.Intent;

import me.itangqi.buildingblocks.R;

/**
 * Created by tangqi on 9/17/15.
 */
public class ShareUtils {

    public static void share(Context context) {
        share(context, context.getString(R.string.about_share_text));
    }

    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.about_menu_action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.about_menu_action_share)));
    }
}
