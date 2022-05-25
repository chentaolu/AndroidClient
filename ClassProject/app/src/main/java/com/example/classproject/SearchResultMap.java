package com.example.classproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;

public class SearchResultMap extends AppCompatActivity implements OnMapReadyCallback {

    static public String selectSchool = "";
    static public String selectCountry = "";
    public LatLng selectLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Search_Map);
        mapFragment.getMapAsync(this);

        Thread thread = new Thread(new MyHandler());
        MyHandler.url = "/GetSchoolDataBySchoolNameAndCountryName?schoolName=" + selectSchool +"&countryName=" + selectCountry;

        thread.start();
        while (!MyHandler.done){
            System.out.println("wait");
        }
        MyHandler.done = false;

        try {
            Float latitude = Float.valueOf(MyHandler.returnResult.getString("latitude"));
            Float longitude = Float.valueOf(MyHandler.returnResult.getString("longitude"));
            selectLatLng = new LatLng(latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        BitmapDescriptor descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        MarkerOptions marker = new MarkerOptions().position(selectLatLng).title(selectSchool).icon(descriptor);

        googleMap.addMarker(marker);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectLatLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}