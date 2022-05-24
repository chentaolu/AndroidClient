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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;

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



        Thread getMessage = new Thread(new MyArrayHandler());
        MyArrayHandler.url = "/GetAllSchoolCoordinate";

        getMessage.start();
        while (!MyArrayHandler.done){
            System.out.println("wait");
        }
        MyArrayHandler.done = false;

        for(int i = 0; i < MyArrayHandler.returnResult.length(); i++) {
            try {
                String longitude = MyArrayHandler.returnResult.getJSONObject(i).get("longitude").toString();
                String latitude = MyArrayHandler.returnResult.getJSONObject(i).get("latitude").toString();
                String schoolName = MyArrayHandler.returnResult.getJSONObject(i).get("schoolName").toString();
                LatLng resultMark = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
                BitmapDescriptor descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                mMap.addMarker(new MarkerOptions().position(resultMark).title(schoolName).icon(descriptor));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


}