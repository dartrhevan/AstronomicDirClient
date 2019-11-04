package com.example.astronomicdirclient.Model;

import org.joda.time.DateTime;

import java.util.Date;

interface ISpaceObject {
    byte[] getPhoto();

    void setPhoto(byte[] photo);

    String getName();

    void setName(String name);

    Distance getMiddleDistance();

    void setMiddleDistance(Distance middleDistance);

    int getRadius();

    void setRadius(int radius);

    int getTemperature();

    void setTemperature(int temperature);

    DateTime getInventingDate();

    void setInventingDate(DateTime inventingDate);
}
