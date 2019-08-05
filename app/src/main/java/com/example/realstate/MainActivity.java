package com.example.realstate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realstate.adapters.HouseAdapter;
import com.example.realstate.models.House;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private HouseAdapter adapter;
    private List<House> houseList;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getHouseListFromDB();
        showData();
        floatingActionButtonSetOnClickListener();

    }

    private void floatingActionButtonSetOnClickListener() {
        floatingActionButton.setOnClickListener(view -> {
            floatingActionButton.setEnabled(false);
            floatingActionButton.setClickable(false);
            startActivity(new Intent(MainActivity.this, AddPropertyActivity.class));
            final Handler handler = new Handler(); //delay for user cant abuse  click
            handler.postDelayed(() -> {
                floatingActionButton.setEnabled(true);
                floatingActionButton.setClickable(true);
            }, 100);
        });
    }

    private void showData() {
        adapter = new HouseAdapter(houseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void getHouseListFromDB() {
        houseList = new ArrayList<>();
        RealStateAppSQLiteOpenHelper realStateAppSQLiteOpenHelper = new RealStateAppSQLiteOpenHelper(this);
        db = realStateAppSQLiteOpenHelper.getReadableDatabase();
        String[] colomns = {"_id", "title", "description", "Image_resource_id", "latitude", "longitude"};
        cursor = db.query("RealStateDBTable", colomns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            houseList.add(new House(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3), cursor.getDouble(4), cursor.getDouble(5)));
        }
    }

    private void init() {
        recyclerView = findViewById(R.id.rc_location);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        RealStateAppSQLiteOpenHelper realStateAppSQLiteOpenHelper = new RealStateAppSQLiteOpenHelper(this);
        fillDB(realStateAppSQLiteOpenHelper);

    }

    private void fillDB(RealStateAppSQLiteOpenHelper realStateAppSQLiteOpenHelper) {
        db = realStateAppSQLiteOpenHelper.getWritableDatabase();
        Drawable drawable = getDrawable(R.drawable.home1);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data=stream.toByteArray();
        realStateAppSQLiteOpenHelper.insertIntoDB(db, data, "in Main Street", "2 bedrooms\n 2 baths \n 1 kitchen", 32.623751, 51.635776);
        drawable = getDrawable(R.drawable.home2);
        bitmap = ((BitmapDrawable)drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        data=stream.toByteArray();
        realStateAppSQLiteOpenHelper.insertIntoDB(db, data, "Oak Wood Avenue", "very healthy environment\n very nice neighbours\n 2 beds 1 kitchen", 32.636354, 51.692590);
        drawable = getDrawable(R.drawable.home3);
        bitmap = ((BitmapDrawable)drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        data=stream.toByteArray();
        realStateAppSQLiteOpenHelper.insertIntoDB(db, data, "Hollywood Boulevard", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Mauris cursus mattis molestie a iaculis at erat pellentesque. Id interdum velit laoreet id donec ultrices tincidunt arcu. Pellentesque habitant morbi tristique senectus et netus. Aenean pharetra magna ac placerat vestibulum lectus. Fringilla est ullamcorper eget nulla facilisi etiam dignissim diam. Laoreet id donec ultrices tincidunt. Eget arcu dictum varius duis at. Facilisis leo vel fringilla est. Venenatis cras sed felis eget velit aliquet sagittis.\n" +
                "\n" +
                "Consectetur adipiscing elit duis tristique sollicitudin nibh. Id leo in vitae turpis massa sed elementum tempus egestas. Augue mauris augue neque gravida in fermentum et. Varius quam quisque id diam. Lorem ipsum dolor sit amet consectetur. Placerat vestibulum lectus mauris ultrices eros in cursus. Maecenas volutpat blandit aliquam etiam. Nec feugiat nisl pretium fusce id velit ut tortor pretium. Eget mauris pharetra et ultrices neque ornare aenean. Lacus vestibulum sed arcu non odio euismod lacinia. Volutpat ac tincidunt vitae semper quis lectus nulla at. Cras sed felis eget velit. Erat velit scelerisque in dictum non consectetur a erat nam. Ut lectus arcu bibendum at. Tincidunt eget nullam non nisi est sit amet. Ut morbi tincidunt augue interdum velit. Eu mi bibendum neque egestas congue quisque egestas diam.\n" +
                "\n" +
                "Condimentum mattis pellentesque id nibh. Ac tortor dignissim convallis aenean et tortor. Nisl nunc mi ipsum faucibus. Netus et malesuada fames ac turpis egestas sed tempus. Erat imperdiet sed euismod nisi porta lorem mollis aliquam ut. Ut sem nulla pharetra diam sit amet nisl suscipit. Sit amet nulla facilisi morbi tempus iaculis urna. Blandit libero volutpat sed cras ornare. Vulputate dignissim suspendisse in est. Ut etiam sit amet nisl purus in mollis nunc. Gravida cum sociis natoque penatibus. Sed libero enim sed faucibus turpis in eu mi bibendum. Commodo odio aenean sed adipiscing diam donec. Eget nulla facilisi etiam dignissim diam quis enim. Et tortor consequat id porta. Vel risus commodo viverra maecenas accumsan lacus vel. Metus vulputate eu scelerisque felis imperdiet proin fermentum leo vel. Nisl vel pretium lectus quam id leo in vitae turpis. Sit amet consectetur adipiscing elit ut aliquam purus.\n" +
                "\n" +
                "At erat pellentesque adipiscing commodo elit at. Integer malesuada nunc vel risus commodo viverra maecenas. Eget nulla facilisi etiam dignissim. Fames ac turpis egestas integer eget aliquet. Ultricies leo integer malesuada nunc vel. Turpis nunc eget lorem dolor sed viverra. Porttitor eget dolor morbi non. Sit amet est placerat in egestas erat. Diam maecenas ultricies mi eget mauris pharetra et. Donec ultrices tincidunt arcu non sodales neque sodales ut. Viverra nam libero justo laoreet sit amet. Ac turpis egestas integer eget aliquet nibh praesent. Nunc id cursus metus aliquam eleifend. Risus nec feugiat in fermentum posuere urna. Euismod lacinia at quis risus.\n" +
                "\n" +
                "Lectus urna duis convallis convallis tellus id. Lectus arcu bibendum at varius vel pharetra vel turpis. Etiam dignissim diam quis enim lobortis scelerisque fermentum dui faucibus. Sit amet tellus cras adipiscing. Mauris sit amet massa vitae tortor condimentum lacinia quis. Libero id faucibus nisl tincidunt eget nullam non. Erat velit scelerisque in dictum non consectetur. Urna et pharetra pharetra massa massa ultricies. Integer feugiat scelerisque varius morbi enim nunc. Non arcu risus quis varius quam quisque id diam vel.", 32.671713, 51.676312);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}


