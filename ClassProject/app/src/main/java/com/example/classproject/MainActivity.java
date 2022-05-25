package com.example.classproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    private Button mapBtn;
    static public LatLng currentLocation = new LatLng(24.2499376,120.7234986 );
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    private LinearLayout schoolsBtn;
    public String currentCountry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapBtn = (Button) findViewById(R.id.ChangeToMap_Btn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapPage = new Intent();
                mapPage.setClass(MainActivity.this, mapAct.class);
                startActivity(mapPage);
            }
        });
        /*
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }*/
        schoolsBtn = findViewById(R.id.units_group);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread getMessage = new Thread(new MyArrayHandler());

        MyArrayHandler.url = "/GetSchoolDataByCurrentLocation?" + "latitude=" + String.valueOf(currentLocation.latitude) + "&longitude=" + String.valueOf(currentLocation.longitude);
        getMessage.start();
        while (!MyArrayHandler.done){
            System.out.println("wait");
        }
        MyArrayHandler.done = false;
        try {
            currentCountry = MyArrayHandler.returnResult.getJSONObject(0).getString("countryName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            for(int i = 0; i < MyArrayHandler.returnResult.length(); i++) {
                System.out.println(MyArrayHandler.returnResult.getJSONObject(i).get("schoolName"));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        200
                );

                Button school = new Button(this);
                school.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button currentBtn = (Button) view;
                        SearchResultMap.selectCountry = currentCountry;
                        SearchResultMap.selectSchool = currentBtn.getText().toString();
                        Intent searchResult = new Intent();
                        searchResult.setClass(MainActivity.this, SearchResultMap.class);
                        startActivity(searchResult);
                    }
                });




                school.setLayoutParams(params);
                school.setText(MyArrayHandler.returnResult.getJSONObject(i).get("schoolName").toString());
                school.setId(i);
                schoolsBtn.addView(school);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                currentLocation = new LatLng(lat, longi);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    /*
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    */
}