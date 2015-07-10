package me.itangqi.testproj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import me.itangqi.testproj.bean.DailyNews;


public final class ZhuanlanDataSource {
    private SQLiteDatabase database;
    private ZhuanlanDBHelper dbHelper;
    private String[] allColumns = {
            ZhuanlanDBHelper.COLUMN_ID,
            ZhuanlanDBHelper.COLUMN_DATE,
            ZhuanlanDBHelper.COLUMN_CONTENT
    };

    public ZhuanlanDataSource(Context context) {
        dbHelper = new ZhuanlanDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public List<DailyNews> insertDailyNewsList(String date, String content) {
        ContentValues values = new ContentValues();
        values.put(ZhuanlanDBHelper.COLUMN_DATE, date);
        values.put(ZhuanlanDBHelper.COLUMN_CONTENT, content);

        long insertId = database.insert(ZhuanlanDBHelper.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(ZhuanlanDBHelper.TABLE_NAME,
                allColumns, ZhuanlanDBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        List<DailyNews> newsList = cursorToNewsList(cursor);
        cursor.close();
        return newsList;
    }

    public void updateNewsList(String date, String content) {
        ContentValues values = new ContentValues();
        values.put(ZhuanlanDBHelper.COLUMN_DATE, date);
        values.put(ZhuanlanDBHelper.COLUMN_CONTENT, content);
        database.update(ZhuanlanDBHelper.TABLE_NAME, values, ZhuanlanDBHelper.COLUMN_DATE + "=" + date, null);
    }

    public void insertOrUpdateNewsList(String date, String content) {
        if (newsOfTheDay(date) != null) {
            updateNewsList(date, content);
        } else {
            insertDailyNewsList(date, content);
        }
    }

    // That reminds you of Queen, huh? ;-)
    public List<DailyNews> newsOfTheDay(String date) {
        Cursor cursor = database.query(ZhuanlanDBHelper.TABLE_NAME,
                allColumns, ZhuanlanDBHelper.COLUMN_DATE + " = " + date, null,
                null, null, null);

        cursor.moveToFirst();
        List<DailyNews> newsList = cursorToNewsList(cursor);
        cursor.close();
        return newsList;
    }

    private List<DailyNews> cursorToNewsList(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            String string = cursor.getString(2);
            Type listType = new TypeToken<List<DailyNews>>() {

            }.getType();

            return new GsonBuilder().create().fromJson(string, listType);
        } else {
            return null;
        }
    }
}
