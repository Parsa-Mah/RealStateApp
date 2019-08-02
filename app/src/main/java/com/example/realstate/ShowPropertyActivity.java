package com.example.realstate;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.realstate.models.House;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowPropertyActivity extends AppCompatActivity implements OnMapReadyCallback {
    AppCompatImageView imageViewShowProperty;
    AppCompatTextView textViewTitle , textViewDescription;
    House house;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property);
        init();
        bindDataToWidgets();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(house.getLatitude(), house.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*
         * Request all parents to relinquish the touch events
         */
        mMapView.getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

}
