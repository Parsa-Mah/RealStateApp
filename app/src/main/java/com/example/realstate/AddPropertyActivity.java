package com.example.realstate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.realstate.models.House;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class AddPropertyActivity extends AppCompatActivity {

    AppCompatEditText editTextTitle, editTextDescription;
    AppCompatImageView imgvAddLocation, addImage;
    AppCompatButton buttonAddProperty;
    House house = new House();
    private String cameraFilePath;
    private static final int LOCATION_SAVED_REQUEST_CODE;
    private static final int GALLERY_REQUEST_CODE;
    private static final int CAMERA_REQUEST_CODE;

    static {
        LOCATION_SAVED_REQUEST_CODE = 2;
        GALLERY_REQUEST_CODE = 3;
        CAMERA_REQUEST_CODE = 4;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                assert data != null;
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                assert selectedImage != null;
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                house.setAvatar(imgDecodableString);
                Log.i("flp", imgDecodableString);
                cursor.close();
                addImage.setImageResource(0);
                addImage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap bitmap = BitmapFactory.decodeFile(cameraFilePath.toString());
                house.setAvatar(cameraFilePath);
                addImage.setImageBitmap(bitmap);
            } else if (requestCode == LOCATION_SAVED_REQUEST_CODE) {
                assert data != null;
                house = data.getParcelableExtra("loc");
                Toast.makeText(this, R.string.location_saved, Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == MapsActivity.PERMISSION_CANCELED) {
            if (requestCode == LOCATION_SAVED_REQUEST_CODE) {
                Toast.makeText(this, R.string.access_permission_canceled, Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == MapsActivity.LOCATION_OFF) {
            if (requestCode == LOCATION_SAVED_REQUEST_CODE) {
                Toast.makeText(this, R.string.location_saved_canceled, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        init();

        addImage.setOnClickListener(View -> new AlertDialog.Builder(this).setTitle("Chose your Option!").setMessage("How do you want to proceed")
                .setPositiveButton("CAMERA", (dialogInterface, i) -> {
                    dispatchTakePictureIntent();
                    //captureFromCamera();
                }).setNegativeButton("GALERY", (DialogInterface, i) -> {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    String[] mimeTypes = {"image/jpeg", "image/png"};
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                }).show());


        imgvAddLocation.setOnClickListener(view -> {
            Intent intent = new Intent(AddPropertyActivity.this, MapsActivity.class);
            intent.putExtra("loc", house);
            startActivityForResult(intent, LOCATION_SAVED_REQUEST_CODE);
        });
        buttonAddProperty.setOnClickListener(view -> {
            String title = Objects.requireNonNull(editTextTitle.getText()).toString().trim();
            String description = Objects.requireNonNull(editTextDescription.getText()).toString().trim();
            if (isValid(title, description)) {
                if (house.getLongitude() != 0.0 && house.getLongitude() != 0.0) {
                    if (house.getAvatar() != null) {
                        house.setTitle(title);
                        house.setDescription(description);
                        RealStateAppSQLiteOpenHelper db = new RealStateAppSQLiteOpenHelper(AddPropertyActivity.this);
                        db.insertIntoDB(house);
                        db.close();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(this, R.string.pls_add_image, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, R.string.select_location, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isValid(String title, String description) {
        if (title.isEmpty()) {
            Toast.makeText(this, R.string.title_empty, Toast.LENGTH_LONG).show();
            editTextTitle.requestFocus();
            return false;
        } else if (description.isEmpty()) {
            Toast.makeText(this, R.string.description_empty, Toast.LENGTH_LONG).show();
            editTextDescription.requestFocus();
            return false;
        }
        return true;
    }

    private void init() {
        imgvAddLocation = findViewById(R.id.imgv_add_location);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.et_description);
        addImage = findViewById(R.id.imgv_add_property);
        buttonAddProperty = findViewById(R.id.btn_add_property);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(this).setTitle("IMPORTANT").setMessage("In order to use Add Property you need to grant us Camera and Storage Permission")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        try {
                            requestPermission();
                        } catch (Exception e) {
                            Toast.makeText(this, "Please grant \"ALL\" Premissions", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).show();
        }
    }

    public void requestPermission() throws Exception {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                throw new IOException();
            }

        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePicturesIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicturesIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                cameraFilePath = photoFile.getPath();
            } catch (IOException e) {
                Toast.makeText(this, "Error while creating the image file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.realstate.fileprovider", photoFile);
                takePicturesIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicturesIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}

