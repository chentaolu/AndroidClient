package com.example.classproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity {
    EditText schoolname;
    List<String> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        schoolname = (EditText)findViewById(R.id.edt1);

        Thread getMessage = new Thread(new MyArrayHandler());
        MyArrayHandler.url = "/GetAllCountry";
        countries = new ArrayList<String>();

        getMessage.start();
        while (!MyArrayHandler.done){
            System.out.println("wait");
        }
        MyArrayHandler.done = false;

        for(int i = 0; i < MyArrayHandler.returnResult.length(); i++) {
            try {
                countries.add(MyArrayHandler.returnResult.getJSONObject(i).get("countryName").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        System.out.println(R.array.planets_array);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, countries);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(2, false);
        spinner.setOnItemSelectedListener(spnOnItemSelected);

        Button submit = (Button)findViewById(R.id.button1);
        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }
    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            String sPos=String.valueOf(pos);
            String sInfo=parent.getItemAtPosition(pos).toString();
            //String sInfo=parent.getSelectedItem().toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
