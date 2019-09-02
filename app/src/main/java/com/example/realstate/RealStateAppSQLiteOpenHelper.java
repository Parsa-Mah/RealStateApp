package com.example.realstate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.realstate.models.House;

import java.util.ArrayList;

public class RealStateAppSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String KEY_IMAGE_FILE_PATH;
    private static final String KEY_TITLE;
    private static final String KEY_DESCRIPTION;
    private static final String KEY_LATITUDE;
    private static final String KEY_LONGITUDE;

    static {
        KEY_IMAGE_FILE_PATH = "Image_file_path";
        KEY_TITLE = "title";
        KEY_DESCRIPTION = "description";
        KEY_LATITUDE = "latitude";
        KEY_LONGITUDE = "longitude";
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE RealStateDBTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, Image_file_path TEXT, title TEXT, description TEXT, latitude DOUBLE, longitude DOUBLE)");
    }


    public void insertIntoDB(House house) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IMAGE_FILE_PATH, house.getAvatar());
        contentValues.put(KEY_TITLE, house.getTitle());
        contentValues.put(KEY_DESCRIPTION, house.getDescription());
        contentValues.put(KEY_LATITUDE, house.getLatitude());
        contentValues.put(KEY_LONGITUDE, house.getLongitude());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("RealStateDBTable", null, contentValues);
        db.close();
    }

    public ArrayList<House> readFromDB(int id) {
        String[] columns = {KEY_IMAGE_FILE_PATH, KEY_TITLE, KEY_DESCRIPTION, KEY_LATITUDE, KEY_LONGITUDE};
        String selection = null;
        if (id != -1) {
            selection = "_id=" + id;
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("RealStateDBTable", columns, selection, null, null, null, null);
        ArrayList<House> houseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                House house = new House();
                house.setAvatar(cursor.getString(0));
                house.setTitle(cursor.getString(1));
                house.setDescription(cursor.getString(2));
                house.setLatitude(cursor.getDouble(3));
                house.setLongitude(cursor.getDouble(4));
                houseList.add(house);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return houseList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    RealStateAppSQLiteOpenHelper(Context context) {
        super(context, "RealStateAppDB", null, 1);
    }
}
