package com.example.classproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import java.util.List;

public class mapAct extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    Button search, change, webPageBtn;
    int count=1;
    private GoogleMap mMap;
    private String selectSchool = "";
    private String selectCountry = "";
    private List<SchoolData> schoolDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (Button)findViewById(R.id.btsearch);
        webPageBtn = (Button) findViewById(R.id.webPage);
        schoolDataList = new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schoolSearch = new Intent();
                schoolSearch.setClass(mapAct.this, SearchPage.class);
                startActivity(schoolSearch);
            }
        });

        webPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectSchool.equals("") && !selectCountry.equals("")) {
                    Thread getMessage = new Thread(new MyHandler());
                    MyHandler.url = "/GetSchoolDataBySchoolNameAndCountryName?schoolName=" + selectSchool + "&countryName=" + selectCountry;

                    getMessage.start();
                    while (!MyHandler.done){
                        System.out.println("wait");
                    }
                    MyHandler.done = false;
                    String url = "https://www.google.com.tw/?hl=zh_TW";
                    try {
                        url = MyHandler.returnResult.get("web_page").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        getApplicationContext().startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intent.setPackage(null);
                        getApplicationContext().startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select a Mark", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
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
                LatLng resultMark = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));

                String schoolName = MyArrayHandler.returnResult.getJSONObject(i).get("schoolName").toString();
                String countryName = MyArrayHandler.returnResult.getJSONObject(i).get("countryName").toString();
                BitmapDescriptor descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                MarkerOptions marker = new MarkerOptions().position(resultMark).title(schoolName).icon(descriptor);
                schoolDataList.add(new SchoolData(resultMark, schoolName, countryName));

                mMap.addMarker(marker);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        for(SchoolData school : schoolDataList) {
            if(marker.getPosition().equals(school.latLng)) {
                selectSchool = school.schoolName;
                selectCountry = school.countryName;
            }
        }

        return false;
    }
}