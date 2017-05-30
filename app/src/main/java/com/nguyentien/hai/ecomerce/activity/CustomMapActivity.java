package com.nguyentien.hai.ecomerce.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.ultil.CheckConnection;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_map);
        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng nuceShop = new LatLng(21.004256, 105.842054);
        mMap.addMarker(new MarkerOptions().position(nuceShop).title("Nuce Shop"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nuceShop));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nuceShop,18));
    }

}
