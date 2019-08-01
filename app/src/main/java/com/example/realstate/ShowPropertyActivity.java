package com.example.realstate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import com.example.realstate.models.House;

public class ShowPropertyActivity extends AppCompatActivity {
    AppCompatImageView imageViewShowProperty;
    AppCompatTextView textViewTitle , textViewDescription;
    House house;
    /*
    SQLiteDatabase db;
    Cursor cursor;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property);
        init();
        imageViewShowProperty.setImageResource(Integer.parseInt(house.getAvatarPath()));
        textViewTitle.setText(house.getTitle());
        textViewDescription.setText(house.getDescription());
        /*
        int houseId = getIntent().getIntExtra("loc");
        Toast.makeText(this, "The id is"+ houseId, Toast.LENGTH_LONG).show();

        RealStateAppSQLiteOpenHelper openHelper = new RealStateAppSQLiteOpenHelper(this);
        db = openHelper.getReadableDatabase();
        String[] dbArray = { "title", "description", "Image_resource_id"};
        String[] dbSelection = {houseId+""};
        cursor = db.query("RealStateDBTable", dbArray, "_id=?", dbSelection, null, null, null);
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            textViewTitle.setText(title);
            String description = cursor.getString(cursor.getColumnIndex("description"));
            textViewDescription.setText(description);
            imageViewShowProperty.setImageResource(cursor.getInt(cursor.getColumnIndex("Image_resource_id")));
        }
        */
    }

    private void init() {

        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewShowProperty = findViewById(R.id.imageViewShowProperty);
        house = getIntent().getParcelableExtra("loc");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        db.close();
        cursor.close();
        */
    }
}
