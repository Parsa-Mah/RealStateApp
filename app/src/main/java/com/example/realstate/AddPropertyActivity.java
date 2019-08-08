package com.example.realstate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

    AppCompatEditText editTextTitle ,editTextDescription;
    AppCompatImageView imgvAddLocation,addImage;
    AppCompatButton buttonAddProperty;
    House house = new House();
    private String cameraFilePath;
    private static final int SELECTED_IMAGE;
    private static final int LOCATION_SAVED;
    private static final int GALLERY_REQUEST_CODE;
    private static final int CAMERA_REQUEST_CODE;

    static {
        SELECTED_IMAGE = 1;
        LOCATION_SAVED = 2;
        GALLERY_REQUEST_CODE = 3;
        CAMERA_REQUEST_CODE = 4;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                addImage.setImageResource(0);
                addImage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                captureFromCamera();
                addImage.setImageURI(Uri.parse(cameraFilePath));
            } else if (requestCode == LOCATION_SAVED) {
                assert data != null;
                house = data.getParcelableExtra("loc");
            }
        } else if (resultCode == MapsActivity.PERMISSION_CANCELED) {
            if (requestCode == LOCATION_SAVED) {
                Toast.makeText(this, R.string.access_permission_canceled, Toast.LENGTH_LONG).show();
            }
        }  else if (resultCode == MapsActivity.LOCATION_OFF) {
            if (requestCode == LOCATION_SAVED) {
                Toast.makeText(this, R.string.location_saved_canceled, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        init();

        addImage.setOnClickListener(View -> {
            new AlertDialog.Builder(this).setTitle("Chose your Option!").setMessage("How do you want to proceed")
                    .setPositiveButton("CAMERA", (dialogInterface, i) -> {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }).setNegativeButton("GALERY", (DialogInterface, i) -> {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }).show();
        });


        imgvAddLocation.setOnClickListener(view -> {
            Intent intent = new Intent(AddPropertyActivity.this, MapsActivity.class);
            intent.putExtra("loc", house);
            startActivityForResult(intent, LOCATION_SAVED);
        });
        buttonAddProperty.setOnClickListener(view -> {
            String title = Objects.requireNonNull(editTextTitle.getText()).toString().trim();
            String description = editTextTitle.getText().toString().trim();
            if (isValid(title, description)) {
                if (house.getLongitude() != 0.0 && house.getLongitude() != 0.0){

                }else {
                    Toast.makeText(this , R.string.select_location , Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    private boolean isValid(String title, String description) {
        if (title.isEmpty()){
            Toast.makeText(this , R.string.title_empty , Toast.LENGTH_LONG).show();
            editTextTitle.requestFocus();
            return false;

        } else if(description.isEmpty()){
            Toast.makeText(this , R.string.description_empty , Toast.LENGTH_LONG).show();
            editTextTitle.requestFocus();
            return false;
        }
        return true;
    }

    private void init() {
        imgvAddLocation = findViewById(R.id.imgv_add_location);
        editTextTitle = findViewById(R.id.et_titel);
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
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    private void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

