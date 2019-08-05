package com.example.realstate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RealStateAppSQLiteOpenHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE RealStateDBTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, Image_resource_id BLOB, title TEXT, description TEXT, latitude DOUBLE, longitude DOUBLE)");

    }

    public void insertIntoDB(SQLiteDatabase db, byte[] image, String title, String description, Double latitude, Double longitude){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Image_resource_id", image);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        db.insert("RealStateDBTable", null, contentValues);
    }

    public ContentValues readFromDB(SQLiteDatabase db, Cursor cursor){
        ContentValues contentValues = new ContentValues();
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    RealStateAppSQLiteOpenHelper(Context context) {
        super(context, "RealStateDB", null, 1);

    }


}
