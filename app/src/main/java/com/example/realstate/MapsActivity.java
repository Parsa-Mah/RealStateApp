package com.example.realstate;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.realstate.models.House;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        // Add a marker in Isfahan and move the camera
        LatLng isfahan = new LatLng(32.642375, 51.667377);
        mMap.addMarker(new MarkerOptions().position(isfahan).title("Marker in Isfahan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(isfahan, 15f));
        Snackbar.make(findViewById(R.id.map), "Hi Arya", Snackbar.LENGTH_LONG).show();
        mMap.setOnMapLongClickListener(latLng -> {
            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("Marker of Finder"));

            new AlertDialog.Builder(this)
                    .setTitle(R.string.save_location)
                    .setMessage(R.string.save_this_location)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        Intent intent = new Intent();
                        house.setLatitude(latLng.latitude);
                        house.setLongitude(latLng.longitude);
                        intent.putExtra("loc" , house);
                        setResult(RESULT_OK , intent);
                        finish();
                    })
                    .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                        mMap.clear();
                    }).show();


        });
    }
}
