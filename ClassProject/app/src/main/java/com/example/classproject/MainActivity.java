package com.example.classproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;


public class MainActivity extends AppCompatActivity {


    private ImageButton mapBtn;
    public static String url = "https://6b3a-59-126-72-168.ngrok.io//GetAllCountry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapBtn = (ImageButton) findViewById(R.id.ChangeToMap_Btn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapPage = new Intent();
                mapPage.setClass(MainActivity.this, mapAct.class);
                startActivity(mapPage);
            }
        });
        Thread getMessage = new Thread(new MyHandler());
        getMessage.start();
        while(!MyHandler.done) {
            System.out.println("wait");
        }
        try {
            System.out.println(MyHandler.returnResult.get("0"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHandler.done = false;


    }

}