package com.example.realstate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RealStateAppSQLiteOpenHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE RealStateDBTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, Image_resource_id TEXT, title TEXT, description TEXT)");
        insertIntoDB(sqLiteDatabase, R.drawable.home1, "in Main Street", "2 bedrooms\n 2 baths \n 1 kitchen");
        insertIntoDB(sqLiteDatabase, R.drawable.home2, "Oak Wood Avenue", "very healthy environment\n very nice neighbours\n 2 beds 1 kitchen");
        insertIntoDB(sqLiteDatabase, R.drawable.home3, "Hollywood Boulevard", "A big castle with 7 rooms\n 3 kitchens\n 5 bathes\n 2000 meters");

    }

    public void insertIntoDB(SQLiteDatabase db, int image, String title, String description){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Image_resource_id", image);
        contentValues.put("title", title);
        contentValues.put("description", description);
        db.insert("RealStateDBTable", null, contentValues);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public RealStateAppSQLiteOpenHelper(Context context) {
        super(context, null, null, 1);

    }
}
