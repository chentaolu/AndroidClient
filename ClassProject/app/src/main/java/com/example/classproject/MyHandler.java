package com.example.classproject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyHandler implements Runnable {

    static JSONObject returnResult;
    
    @Override
    public void run() {
        try {
            String result = Connection.getJSON(MainActivity.url, 9000);

            result = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
            result = result.replace("\\", "");
            System.out.println(result);
            returnResult = new JSONObject(result);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
