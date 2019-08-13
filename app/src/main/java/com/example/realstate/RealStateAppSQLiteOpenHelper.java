package com.example.realstate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.realstate.models.House;

import java.util.ArrayList;

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
        db.close();
    }

    public ArrayList<House> readFromDB(SQLiteDatabase db, int id){
        String[] columns = {"Image_file_path", "title", "description", "latitude", "longitude"};
        String selection = null;
        if (id != -1){
            selection = "_id="+id;
        }
        Cursor cursor = db.query("RealStateDBTable", columns,selection, null, null, null, null );
        ArrayList houseList = new ArrayList<House>();
        while (cursor.moveToFirst()){
            House house = new House();
            house.setAvatar(cursor.getString(0));
            house.setTitle(cursor.getString(1));
            house.setDescription(cursor.getString(2));
            house.setLatitude(cursor.getDouble(3));
            house.setLongitude(cursor.getDouble(4));
        }
        db.close();
        cursor.close();
        return houseList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }

    RealStateAppSQLiteOpenHelper(Context context) {
        super(context, "RealStateAppDB", null, 1);
    }
}
