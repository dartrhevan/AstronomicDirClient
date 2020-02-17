package com.example.astronomicdirclient;

import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.XMLService.XMLHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public final class NetHelper {

    private static String editUrlPath = "https://astronomic-directory.azurewebsites.net/Views/EditStar";
    private static String deleteUrlPath = "https://astronomic-directory.azurewebsites.net/Home/Delete?id=";
    private static String listUrlPath = "https://astronomic-directory.azurewebsites.net/Views/StarListXml";
    private static String starUrlPath = "https://astronomic-directory.azurewebsites.net/Views/StarXml?id=";
    private static String starUploadUrlPath = "https://astronomic-directory.azurewebsites.net/Views/StarViews";

    public static String DownloadStarList() throws IOException {
        return DownloadXML(listUrlPath);
    }

    public static String DownloadStar(int id) throws IOException {
        return DownloadXML(starUrlPath + id);
    }

    private static String DownloadXML(String url1) throws IOException {
        URL url = new URL(url1);
        HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
        connection.setRequestMethod("GET"); // установка метода получения данных -GET
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000); // установка таймаута перед выполнением - 10 000 миллисекунд
        connection.connect(); // подключаемся к ресурсу
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line = null;
        StringBuilder xmlResult = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            xmlResult.append(line);
        }

        reader.close();
        return xmlResult.toString();
    }

    public static String UploadStar(Star st) {
        try {
            String xml = XMLHelper.SerializeStar(st);
            URL url = new URL(starUploadUrlPath);
            HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
            connection.setRequestMethod("POST"); // установка метода получения данных -GET
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000); // установка таймаута перед выполнением - 10 000 миллисекунд
            connection.connect(); // подключаемся к ресурсу
            //connection.getOutputStream()
            try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
                wr.write(xml);
            }
            return connection.getResponseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error!";
    }

    public static String DeleteStar(int id) throws IOException {

        URL url = new URL(deleteUrlPath + id);
        HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
        connection.setRequestMethod("GET"); // установка метода получения данных -GET
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000); // установка таймаута перед выполнением - 10 000 миллисекунд
        connection.connect(); // подключаемся к ресурсу

        return connection.getResponseMessage();
    }

    public static String EditStar(int id, Star star) {
        try {
            String xml = XMLHelper.SerializeStarPair(id, star);
            URL url = new URL(editUrlPath);
            HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
            connection.setRequestMethod("POST"); // установка метода получения данных -GET
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000); // установка таймаута перед выполнением - 10 000 миллисекунд
            connection.connect(); // подключаемся к ресурсу
            //connection.getOutputStream()
            try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
                wr.write(xml);
            }
            return connection.getResponseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error!";
    }
}
