package me.itangqi.buildingblocks.domin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Troy on 2015/9/23.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static String CREATE_DAILYGSON_TABLE = "CREATE TABLE daily("
            + "id smallint PRIMARY KEY NOT NULL,"
            + "title text NOT NULL,"
            + "type smallint NOT NULL,"
            + "image_source text NULL,"
            + "image text NULL,"
            + "share_url text NULL,"
            + "ga_prefix smallint NULL,"
            + "body text NULL)";
    

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
