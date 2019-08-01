package com.example.realstate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.realstate.models.House;

public class ShowPropertyActivity extends AppCompatActivity {
    AppCompatImageView imageViewShowProperty;
    AppCompatTextView textViewTitle , textViewDescription;
    House house;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property);
        init();
        bindDataToWidgets();
    }

    private void bindDataToWidgets() {
        imageViewShowProperty.setImageResource(Integer.parseInt(house.getAvatarPath()));
        textViewTitle.setText(house.getTitle());
        textViewDescription.setText(house.getDescription());
    }

    private void init() {
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewShowProperty = findViewById(R.id.imageViewShowProperty);
        house = getIntent().getParcelableExtra("loc");//get house object from previous activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
