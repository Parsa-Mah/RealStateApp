package com.example.realstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPropertyActivity extends AppCompatActivity {
    ImageView imageViewShowProperty;
    TextView textViewTitle;
    TextView textViewDescription;
    SQLiteDatabase db;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property);

        Intent intent = getIntent();
        int houseId = intent.getIntExtra("loc", -2) + 1;
        Toast.makeText(this, "The id is"+ houseId, Toast.LENGTH_LONG).show();

        textViewDescription = (TextView)findViewById(R.id.textViewDescription);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewShowProperty = findViewById(R.id.imageViewShowProperty);
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
    }
}
