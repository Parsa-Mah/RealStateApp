package com.example.realstate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realstate.adapters.HouseAdapter;
import com.example.realstate.models.House;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private HouseAdapter adapter;
    private List<House> houseList;
    RealStateAppSQLiteOpenHelper realStateAppSQLiteOpenHelper;
    private static final int ADD_PROPERTY_REQ_CODE;

    static {
        ADD_PROPERTY_REQ_CODE = 391;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getHouseListFromDB();
        showData();
        floatingActionButtonSetOnClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_PROPERTY_REQ_CODE) {
                houseList = realStateAppSQLiteOpenHelper.readFromDB(-1);
                adapter.setHouseList(houseList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void floatingActionButtonSetOnClickListener() {
        floatingActionButton.setOnClickListener(view -> {
            floatingActionButton.setEnabled(false);
            floatingActionButton.setClickable(false);
            startActivityForResult(new Intent(MainActivity.this, AddPropertyActivity.class), ADD_PROPERTY_REQ_CODE);
            final Handler handler = new Handler(); //delay for user cant abuse  click
            handler.postDelayed(() -> {
                floatingActionButton.setEnabled(true);
                floatingActionButton.setClickable(true);
            }, 100);
        });
    }

    private void showData() {
        adapter = new HouseAdapter(houseList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getHouseListFromDB() {
        houseList = new ArrayList<>();
        RealStateAppSQLiteOpenHelper realStateAppSQLiteOpenHelper = new RealStateAppSQLiteOpenHelper(this);
        houseList = realStateAppSQLiteOpenHelper.readFromDB(-1);
    }

    private void init() {
        realStateAppSQLiteOpenHelper = new RealStateAppSQLiteOpenHelper(this);
        recyclerView = findViewById(R.id.rc_location);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realStateAppSQLiteOpenHelper.close();
    }
}


