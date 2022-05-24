package com.example.classproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class mapAct extends FragmentActivity implements OnMapReadyCallback {

    Button search, change;
    int count=1;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (Button)findViewById(R.id.btsearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schoolsearch = new Intent();
                schoolsearch.setClass(mapAct.this, SearchPage.class);
                startActivity(schoolsearch);
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        change = (Button)findViewById(R.id.btchange);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(count % 4 + 1);
                count++;
            }
        });

        LatLng taiwan = new LatLng(23.973875, 120.982024);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(taiwan));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8));
    }
}