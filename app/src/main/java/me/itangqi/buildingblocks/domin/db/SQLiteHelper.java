package me.itangqi.buildingblocks.domin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Troy on 2015/9/23.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static String CREATE_DAILYGSON_TABLE = "CREATE TABLE daily("
            + "id int primary key,"
            + "title text,"
            + "image_source text,"
            + "image text,"
            + "share_url text,"
            + "ga_prefix int,"
            + "body text)";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DAILYGSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
