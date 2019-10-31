package com.example.astronomicdirclient;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public final class Downloader{
    private static String urlPath = "http://192.168.0.103:3000/Views/StarListXml";


    public static String DownloadStarList() throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // установка метода получения данных -GET
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000); // установка таймаута перед выполнением - 10 000 миллисекунд
        connection.connect(); // подключаемся к ресурсу
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line=null;
        StringBuilder xmlResult = new StringBuilder();
        while ((line=reader.readLine()) != null) {
            xmlResult.append(line);
        }

        reader.close();
        return xmlResult.toString();
    }
}
