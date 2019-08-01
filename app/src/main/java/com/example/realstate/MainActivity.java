package com.example.realstate;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realstate.adapters.HouseAdapter;
import com.example.realstate.models.House;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HouseAdapter adapter;
    private List<House> houseList;
    private SQLiteDatabase db;
    private Cursor cursor;
    ArrayList columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //ToDo: get location list from sqlite
        simulateHouse();
        showData();

    }

    private void showData() {
        adapter = new HouseAdapter(houseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void simulateHouse() {
        houseList = new ArrayList<>();
        RealStateAppSQLiteOpenHelper realStateAppSQLiteOpenHelper = new RealStateAppSQLiteOpenHelper(this);
        db = realStateAppSQLiteOpenHelper.getReadableDatabase();
        String[] colomns = {"_id", "title", "description", "Image_resource_id"};
        cursor = db.query("RealStateDBTable", colomns, null, null, null, null, null);
        while(cursor.moveToNext()){
            houseList.add(new House(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }


    }

    private void init() {
        recyclerView = findViewById(R.id.rc_location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}


