package me.itangqi.buildingblocks.domin.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.model.entity.Daily;

public class CommonUtils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

}
