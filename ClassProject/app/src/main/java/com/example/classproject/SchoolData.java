package com.example.classproject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SchoolData {
    public LatLng latLng;
    public String schoolName;
    public String countryName;

    public SchoolData(LatLng latLng, String schoolName, String countryName){
        this.latLng = latLng;
        this.countryName = countryName;
        this.schoolName = schoolName;
    }
}
