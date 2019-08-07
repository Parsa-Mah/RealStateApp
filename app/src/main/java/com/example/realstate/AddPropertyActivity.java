package com.example.realstate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.realstate.models.House;

import java.io.IOException;
import java.util.Objects;

public class AddPropertyActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDescription;
    Button buttonAddLocation;
    Button buttonAddProperty;
    House house = new House();
    ImageView imageView;
    private static final int SELECTED_IMAGE;
    private static final int LOCATION_SAVED;

    static {
        SELECTED_IMAGE = 1;
        LOCATION_SAVED = 2;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECTED_IMAGE) {
            Uri uri = Objects.requireNonNull(data).getData();
            String[] p = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, p, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(p[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Drawable drawable = new BitmapDrawable(AddPropertyActivity.this.getResources(), bitmap);
            imageView.setBackground(drawable);
        }else if (resultCode == RESULT_OK && requestCode == LOCATION_SAVED){
            assert data != null;
            house = data.getParcelableExtra("loc");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        init();

        imageView.setOnClickListener(View -> {

            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_IMAGE);
        });


        buttonAddLocation.setOnClickListener(view -> {
          //  String title = editTextTitle.getText().toString().trim();
          //  String description = editTextDescription.getText().toString().trim();
           // if (isValid(title, description)) {
                Intent intent = new Intent(AddPropertyActivity.this, MapsActivity.class);
               // House house = new House();
               // house.setTitle("title");
                //house.setDescription("description");
                intent.putExtra("loc", house);
                startActivityForResult(intent, LOCATION_SAVED);
         //   }


        });
    }

    private boolean isValid(String title, String description) {
        if (title.isEmpty() && description.isEmpty())
            return false;//todo:avaz kardan return ha + if is empty edittext request foucus to this edittext+show toast for error
        //todo:check kardan mahdoodiyat 3<title <50 va 5<description<500
        return true;
    }

    private void init() {
        buttonAddLocation = findViewById(R.id.buttonAddLocation);
        editTextTitle = findViewById(R.id.editTextTitel);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageView = findViewById(R.id.imageViewAddProperty);
        buttonAddProperty = findViewById(R.id.buttonAddProperty);
        try {
            requestPermission();
        } catch (Exception e) {
            Toast.makeText(this, "Please grant \"ALL\" Premissions", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void requestPermission() throws Exception {
        new AlertDialog.Builder(this).setTitle("IMPORTANT").setMessage("In order to use Add Property you need to grant us Camera and Storage Permission")
                .setPositiveButton("OK", null).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                throw new IOException();
            }

        }

    }
}

