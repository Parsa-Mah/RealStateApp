package com.example.realstate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class RealStateAppSQLiteOpenHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE RealStateDBTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, Image_file_path TEXT, title TEXT, description TEXT, latitude DOUBLE, longitude DOUBLE)");
    }

    public void insertIntoDB(SQLiteDatabase db, String image, String title, String description, Double latitude, Double longitude){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Image_file_path", image);
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
        super(context, "RealStateAppDB", null, 1);

    }
    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }
}
