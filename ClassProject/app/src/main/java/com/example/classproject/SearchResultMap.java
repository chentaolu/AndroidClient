package com.example.classproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;

public class SearchResultMap extends AppCompatActivity implements OnMapReadyCallback {

    static public String selectSchool = "";
    static public String selectCountry = "";
    public LatLng selectLatLng;
    Button start, stop, launch1, launch2, launch3;
    private GoogleMap mMap;
    String uriString;

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

        start = (Button)findViewById(R.id.btstart);
        stop = (Button)findViewById(R.id.btstop);

        launch1 = (Button)findViewById(R.id.btdrive);
        launch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.currentCountry.equals(selectCountry)) {
                    uriString = String.format("google.navigation:q=%f,%f,&mode=d", selectLatLng.latitude, selectLatLng.longitude);
                    launchMap();
                } else {
                    Toast.makeText(getApplicationContext(), "請先到本國的國際機場", Toast.LENGTH_SHORT).show();
                }
            }
        });
        launch2 = (Button)findViewById(R.id.btwalk);
        launch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.currentCountry.equals(selectCountry)) {
                    uriString = String.format("google.navigation:q=%f,%f,&mode=w", selectLatLng.latitude, selectLatLng.longitude);
                    launchMap();
                } else {
                    Toast.makeText(getApplicationContext(), "請先到本國的國際機場", Toast.LENGTH_SHORT).show();
                }
            }
        });
        launch3 = (Button)findViewById(R.id.btbicycle);
        launch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.currentCountry.equals(selectCountry)) {
                    uriString = String.format("google.navigation:q=%f,%f,&mode=b", selectLatLng.latitude, selectLatLng.longitude);
                    launchMap();
                } else {
                    Toast.makeText(getApplicationContext(), "請先到本國的國際機場", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void launchMap(){
        Uri intentUri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
        intent.setPackage("com.google.android.apps.maps");

        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        BitmapDescriptor descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        MarkerOptions marker = new MarkerOptions().position(selectLatLng).title(selectSchool).icon(descriptor);

        googleMap.addMarker(marker);


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(MainActivity.currentLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        final CameraPosition cameraPosition = new CameraPosition.Builder().target(selectLatLng).zoom(12).build();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),2000, new GoogleMap.CancelableCallback(){
                    @Override
                    public void onFinish() {
                        Log.d("Map", "Animation finished");
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Map", "Animation interrupted");
                    }
                });
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.stopAnimation();
            }
        });
    }
}