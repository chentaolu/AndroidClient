package com.example.classproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.net.HttpURLConnection;


public class searchpage extends AppCompatActivity {

    EditText schoolname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("searchpage");
        setContentView(R.layout.search);
        //接輸入的字
        schoolname = (EditText)findViewById(R.id.edt1);
        String school = (String) schoolname;

        //下拉式選單
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.country_array,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(2, false);
        spinner.setOnItemSelectedListener(spnOnItemSelected);
        String country = (String) spinner.getSelectedItem();

                // 按鈕
        Button submit = (Button)findViewById(R.id.button);

        submit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

            }
        });
    }
    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

}