package com.example.realstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPropertyActivity extends AppCompatActivity {

    EditText editTextTitle ;
    EditText editTextDescription ;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        }
    public void passToMap(View view){
        Intent startNewActivity = new Intent(this , MapsActivity.class);
        startActivity(startNewActivity);
    }
    }

