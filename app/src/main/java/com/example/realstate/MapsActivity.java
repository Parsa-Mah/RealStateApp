package com.example.realstate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.realstate.models.House;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int ACCESS_FINE_LOCATION_REQUEST_CODE;
    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE;
    public static final int PERMISSION_CANCELED ;
    public static final int LOCATION_OFF ;
    private GoogleMap mMap;
    private House house;



    static {
        ACCESS_FINE_LOCATION_REQUEST_CODE = 85;
        ACCESS_COARSE_LOCATION_REQUEST_CODE = 69;
        PERMISSION_CANCELED = 32;
        LOCATION_OFF = 44;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

    }

    private void init() {
        house = getIntent().getParcelableExtra("loc");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestPermissions();
        if (canGetLocation()) {
            zoomToMyLocation();
            mapSetOnMapLongClickListener();
            mapSetOnMyLocationButtonClickListener();
        } else {
            showSettingsAlert();

        }


        /*
        LatLng isfahan = new LatLng(32.642375, 51.667377);
        mMap.addMarker(new MarkerOptions().position(isfahan).title("Marker in Isfahan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(isfahan, 15f));
        Snackbar.make(findViewById(R.id.map), "Hi Arya", Snackbar.LENGTH_LONG).show();
        */

    }

    private void zoomToMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        assert locationManager != null;
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void mapSetOnMyLocationButtonClickListener() {
        mMap.setOnMyLocationButtonClickListener(() -> {
            if (!canGetLocation()) {
                showSettingsAlert();
            }
            return false;
        });
    }

    private void mapSetOnMapLongClickListener() {
        mMap.setOnMapLongClickListener(latLng -> {
            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("Marker of Finder"));

            new AlertDialog.Builder(this)
                    .setTitle(R.string.save_location)
                    .setMessage(R.string.save_this_location)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        Intent intent = new Intent();
                        house.setLatitude(latLng.latitude);
                        house.setLongitude(latLng.longitude);
                        intent.putExtra("loc", house);
                        setResult(RESULT_OK, intent);
                        finish();
                    })
                    .setNegativeButton(R.string.no, (dialogInterface, i) -> mMap.clear()).show();


        });
    }

    public void showSettingsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setMessage("Please ")
                .setCancelable(false)
                .setPositiveButton(
                        R.string.yes,
                        (dialog, which) -> {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            dialog.cancel();
                        })
                .setNegativeButton(R.string.go_back, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    setResult(LOCATION_OFF, intent);
                    finish();
                })
                .setNeutralButton(R.string.retry, (dialogInterface, i) -> {
                    if (!canGetLocation()) {
                        showSettingsAlert();
                    }
                })
                .show();


    }

    public boolean canGetLocation() {
        boolean result = true;
        LocationManager lm = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if (lm == null) {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        // exceptions will be thrown if provider is not permitted.
        try {
            assert lm != null;
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            network_enabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!gps_enabled || !network_enabled) {
            result = false;
        }

        return result;
    }

    private boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION_REQUEST_CODE);
        } else {

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Intent intent = new Intent();
                setResult(PERMISSION_CANCELED, intent);
                finish();
                //finish();
                // Permission was denied. Display an error message.
            }
            if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {
                if (permissions.length == 1 &&
                        permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    Intent intent = new Intent();
                    setResult(PERMISSION_CANCELED, intent);
                    finish();
                    //finish();
                    // Permission was denied. Display an error message.
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

