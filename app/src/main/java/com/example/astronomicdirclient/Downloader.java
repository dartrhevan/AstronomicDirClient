package com.example.astronomicdirclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Downloader {

    private static String listUrlPath = "http://192.168.0.103:3000/Views/StarListXml";
    private static String starUrlPath = "http://192.168.0.103:3000/Views/StarXml?id=";

    public static String DownloadStarList() throws IOException {
        return DownloadXML(listUrlPath);
    }

    public static String DownloadStar(int id) throws IOException {
        return DownloadXML(starUrlPath + id);
    }

    private static String DownloadXML(String url1) throws IOException {
        URL url = new URL(url1);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // установка метода получения данных -GET
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000); // установка таймаута перед выполнением - 10 000 миллисекунд
        connection.connect(); // подключаемся к ресурсу
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line = null;
        StringBuilder xmlResult = new StringBuilder();
        while ((line=reader.readLine()) != null) {
            xmlResult.append(line);
        }

        reader.close();
        return xmlResult.toString();
    }
}
