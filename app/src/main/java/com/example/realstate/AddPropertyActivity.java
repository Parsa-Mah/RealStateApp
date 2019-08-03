package com.example.realstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.realstate.models.House;

public class AddPropertyActivity extends AppCompatActivity {

    EditText editTextTitle ;
    EditText editTextDescription ;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        init();


        button.setOnClickListener(view -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            if(isValid(title,description)){
                Intent intent = new Intent( AddPropertyActivity.this , MapsActivity.class);
                House house = new House();
                house.setTitle("title");
                house.setDescription("description");
                intent.putExtra("location" , house);
                startActivity(intent);
            }
        });
    }

    private boolean isValid(String title , String description) {
        return false;
    }

    private void init() {
        button =  findViewById(R.id.buttonAddLocation);
        editTextTitle = findViewById(R.id.editTextTitel);
        editTextDescription = findViewById(R.id.editTextDescription);

    }

}

