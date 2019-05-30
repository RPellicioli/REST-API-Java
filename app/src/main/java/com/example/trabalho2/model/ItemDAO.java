package com.example.trabalho2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabalho2.Connection;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private Connection connection;
    private SQLiteDatabase db;

    public ItemDAO(Context context){
        connection = new Connection(context);
        db = connection.getWritableDatabase();
    }

    public List<Item> GetAllItems() {
        List<Item> items = new ArrayList<>();
        Cursor cursor = db.query("item", new String[]{"id", "name", "description"}, null, null, null, null, "id DESC");

        while(cursor.moveToNext()){
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setDescription(cursor.getString(2));
            items.add(item);
        }

        return items;
    }

    public void DeleteItem(Item item){
        db.delete("item", "id = ?", new String[] {item.getId().toString()});
    }

    public long InsertItem(Item item){
        ContentValues values = new ContentValues();

        values.put("name", item.getName());
        values.put("description", item.getDescription());

        return db.insert("item", null, values);
    }

    public long UpdateItem(Item item){
        ContentValues values = new ContentValues();

        values.put("name", item.getName());
        values.put("description", item.getDescription());

        return db.update("item", values, "id = ?", new String[]{ item.getId().toString() });
    }

    public void Restart(){
        db.execSQL("drop table if exists item");
        db.execSQL("create table item(id integer primary key autoincrement, name varchar(100), description varchar(250))");
    }
}
