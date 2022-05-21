package com.example.classproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyArrayHandler implements Runnable {

    static JSONArray returnResult;
    static boolean done = false;

    @Override
    public void run() {
        try {
            String result = Connection.getJSON(MainActivity.url, 9000);

            result = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
            result = result.replace("\\", "");
            System.out.println(result);
            returnResult = new JSONArray(result);
            done = true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
