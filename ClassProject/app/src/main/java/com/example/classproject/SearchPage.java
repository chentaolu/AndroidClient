package com.example.classproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity {
    List<String> countries;
    List<String> schools;
    String countryInfo;
    String schoolInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);


        Thread getCountryMessage = new Thread(new MyArrayHandler());
        MyArrayHandler.url = "/GetAllCountry";
        countries = new ArrayList<String>();

        getCountryMessage.start();
        while (!MyArrayHandler.done) {
            System.out.println("wait");
        }
        MyArrayHandler.done = false;

        for (int i = 0; i < MyArrayHandler.returnResult.length(); i++) {
            try {
                countries.add(MyArrayHandler.returnResult.getJSONObject(i).get("countryName").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Spinner countryspinner = (Spinner) findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, countries);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countryspinner.setAdapter(adapter);
            countryspinner.setSelection(2, false);
            countryspinner.setOnItemSelectedListener(spnOnItemSelectedcountry);
        }
////////////////////////////選完國家選學校/////////////////////////////
        Thread getSchoolMessage = new Thread(new MyArrayHandler());
        MyArrayHandler.url = "/GetSchoolDataByCountry?country="+String.valueOf(countryInfo);
        schools = new ArrayList<String>();

        getSchoolMessage.start();
        while (!MyArrayHandler.done) {
            System.out.println("wait");
        }
        MyArrayHandler.done = false;

        for (int i = 0; i < MyArrayHandler.returnResult.length(); i++) {
            try {
                countries.add(MyArrayHandler.returnResult.getJSONObject(i).get("schoolName").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Spinner schoolspinner = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, countries);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            schoolspinner.setAdapter(adapter);
            schoolspinner.setSelection(2, false);
            schoolspinner.setOnItemSelectedListener(spnOnItemSelectedschool);
        }

        Button submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private AdapterView.OnItemSelectedListener spnOnItemSelectedcountry
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int countrypos, long id) {
            countryInfo=parent.getItemAtPosition(countrypos).toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    private AdapterView.OnItemSelectedListener spnOnItemSelectedschool
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int schollpos, long id) {
            schoolInfo=parent.getItemAtPosition(schollpos).toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };
}
