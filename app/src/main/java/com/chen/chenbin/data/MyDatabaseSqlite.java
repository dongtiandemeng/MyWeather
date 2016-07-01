package com.chen.chenbin.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.security.auth.login.LoginException;

/**
 * Created by chenbin on 2016/6/19.
 */
public class MyDatabaseSqlite extends SQLiteOpenHelper {
    private static String DATABASE_NAME="Weather.db";
    public static String create_city_weather="create table city_weather ("+
            "_id INTEGER PRIMARY KEY," +
            "city TEXT,"+
            "weather TEXT)";
    private static final int DATABASE_VERSION=1;
    public MyDatabaseSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_city_weather);
        Log.i("mydatabase","mydatabase");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+create_city_weather);
        onCreate(db);
    }
}
