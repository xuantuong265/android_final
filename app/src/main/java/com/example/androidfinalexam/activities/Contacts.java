package com.example.androidfinalexam.activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.androidfinalexam.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Contacts extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
       // mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng KhoaCongNgheThongTin = new LatLng(15.973396, 108.249924);
        mMap.addMarker(new MarkerOptions().position(KhoaCongNgheThongTin).title("Trụ sở của Tiki").snippet("hehe"));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(KhoaCongNgheThongTin).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
