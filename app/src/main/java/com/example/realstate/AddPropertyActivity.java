package com.example.realstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.realstate.models.House;

public class AddPropertyActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDescription;
    Button button;
    int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        init();


        button.setOnClickListener(view -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            switch (mode) {
                case 1:
                    if (isValid(title, description)) {
                        Intent intent = new Intent(AddPropertyActivity.this, MapsActivity.class);
                        House house = new House();
                        house.setTitle("title");
                        house.setDescription("description");
                        intent.putExtra("location", house);
                        startActivity(intent);
                    }
                    break;
                case 2:
                    break;
            }

        });
    }

    private boolean isValid(String title, String description) {
        if (title.isEmpty() && description.isEmpty())
            return true;//todo:avaz kardan return ha + if is empty edittext request foucus to this edittext+show toast for error
        //todo:check kardan mahdoodiyat 3<title <50 va 5<description<500
        return false;
    }

    private void init() {
        button = findViewById(R.id.buttonAddLocation);
        editTextTitle = findViewById(R.id.editTextTitel);
        editTextDescription = findViewById(R.id.editTextDescription);

    }

}

