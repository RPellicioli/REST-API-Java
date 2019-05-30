package com.example.trabalho2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Connection extends SQLiteOpenHelper {

    private static final String name = "fortnite.db";
    private static  final int version = 1;

    public Connection(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table item(id integer primary key autoincrement, name varchar(100), description varchar(250))");
//        db.execSQL("create table images(id integer primary key autoincrement, icon varchar(100), item_id integer)");
//        db.execSQL("create table ratings(id integer primary key autoincrement, total_points integer, item_id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
