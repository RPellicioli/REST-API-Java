package com.example.trabalho2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabalho2.Connection;

public class ItemDAO {

    private Connection connection;
    private SQLiteDatabase db;

    public ItemDAO(Context context){
        connection = new Connection(context);
        db = connection.getWritableDatabase();
    }

    public long Insert(Item item){
        ContentValues values = new ContentValues();

        values.put("id", "123");
        values.put("name", item.getName());

        return db.insert("item", null, values);
    }
}
