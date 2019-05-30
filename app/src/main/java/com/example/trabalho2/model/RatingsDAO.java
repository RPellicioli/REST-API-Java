package com.example.trabalho2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabalho2.Connection;

import java.util.ArrayList;
import java.util.List;

public class RatingsDAO {

    private Connection connection;
    private SQLiteDatabase db;

    public RatingsDAO(Context context){
        connection = new Connection(context);
        db = connection.getWritableDatabase();
    }

    public Ratings GetItemRatings(Integer itemId) {
        Ratings rating = new Ratings();
        Cursor cursor = db.query("ratings", new String[]{"id", "total_points"}, "item_id = ?", new String[] { itemId.toString() }, null, null, null, null);

        Ratings r = new Ratings();
        r.setTotalPoints(cursor.getInt(1));

        return r;
    }

    public long InsertRatings(Ratings r, Integer itemId){
        ContentValues values = new ContentValues();

        values.put("total_points", r.getTotalPoints());
        values.put("item_id", itemId);

        return db.insert("ratings", null, values);
    }

    public long UpdateRatings(Ratings r, Integer itemId){
        ContentValues values = new ContentValues();

        values.put("total_points", r.getTotalPoints());

        return db.update("item", values, "item_id = ?", new String[]{ itemId.toString() });
    }

    public void Restart(){
        db.execSQL("drop table if exists ratings");
        db.execSQL("create table ratings(id integer primary key autoincrement, total_points integer, item_id integer)");
    }
}
