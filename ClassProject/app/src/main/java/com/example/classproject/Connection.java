package com.example.classproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    public static String getJSON(String url, int timeout) throws IOException {
        URL u = new URL(url);
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.setRequestMethod("GET");
        c.setUseCaches(false);
        c.setAllowUserInteraction(false);
        c.setConnectTimeout(timeout);   //设置连接主机超时（单位：毫秒）
        c.setReadTimeout(timeout);      //设置从主机读取数据超时（单位：毫秒）
        c.setRequestProperty("User-Agent","Mozilla/5.0");
        c.connect();
        int status = c.getResponseCode();

        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(),"utf-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
        }
        return null;
    }
}
