package com.example.classproject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyHandler implements Runnable {

    public static String baseUrl = "https://cf3b-59-126-72-168.ngrok.io";
    public static String url = "";
    static JSONObject returnResult;
    static boolean done = false;

    @Override
    public void run() {
        try {
            String result = Connection.getJSON(this.baseUrl + this.url, 9000);

            System.out.println(result);
            returnResult = new JSONObject(result);
            done = true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
