package com.example.realstate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.realstate.models.House;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPropertyActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDescription;
    Button button;
    int mode = 1;
    ImageView imageView;
    private static final int SELECTED_IMAGE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECTED_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    String[] p = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, p, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(p[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    Drawable drawable = new BitmapDrawable(AddPropertyActivity.this.getResources(), bitmap);
                    imageView.setBackground(drawable);
                }
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        init();

        imageView.setOnClickListener(View -> {
            try {
                requestPermissionForReadExtertalStorage();
            }catch (Exception e){
                Toast.makeText(this, "Something bad happened", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_IMAGE);
        });


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
        imageView = findViewById(R.id.imageViewAddProperty);

    }
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x3);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}

