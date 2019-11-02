package com.example.astronomicdirclient.Model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Root(name = "Star")
@Default(DefaultType.FIELD)
@XmlRootElement
public class Star implements ISpaceObject, Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Star star = (Star) o;
        return radius == star.radius &&
                temperature == star.temperature &&
                Objects.equals(galaxy, star.galaxy) &&
                Arrays.equals(photo, star.photo) &&
                Objects.equals(name, star.name) &&
                Objects.equals(middleDistance, star.middleDistance) &&
                Objects.equals(inventingDate, star.inventingDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(galaxy, name, middleDistance, radius, temperature, inventingDate);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "star{" +
                "galaxy='" + galaxy + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", name='" + name + '\'' +
                ", middleDistance=" + middleDistance +
                ", radius=" + radius +
                ", temperature=" + temperature +
                ", inventingDate=" + inventingDate +
                '}';
    }

    public String getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Distance getMiddleDistance() {
        return middleDistance;
    }

    @Override
    public void setMiddleDistance(Distance middleDistance) {
        this.middleDistance = middleDistance;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public Date getInventingDate() {
        return inventingDate;
    }

    @Override
    public void setInventingDate(Date inventingDate) {
        this.inventingDate = inventingDate;
    }

    public Star(String galaxy, byte[] photo, String name, Distance middleDistance, int radius, int temperature, Date inventingDate) {
        this.galaxy = galaxy;
        this.photo = photo;
        this.name = name;
        this.middleDistance = middleDistance;
        this.radius = radius;
        this.temperature = temperature;
        this.inventingDate = inventingDate;
        Planets= new HashSet<>();
    }

    public Star(String galaxy, byte[] photo, String name, Distance middleDistance, int radius, int temperature, Date inventingDate, HashSet<Planet> planets) {
        this.galaxy = galaxy;
        this.photo = photo;
        this.name = name;
        this.middleDistance = middleDistance;
        this.radius = radius;
        this.temperature = temperature;
        this.inventingDate = inventingDate;
        Planets = planets;
    }

    public Star(){Planets= new HashSet<>();}
    @Element(name = "Galaxy")
    @XmlElement(name = "Galaxy")
    private String galaxy;

    @Element(name = "Photo")
    @XmlElement(name = "Photo")
    private byte[] photo;
    @Element(name = "Name")
    @XmlElement(name = "Name")
    private String name;
    @Element(name = "MiddleDistance")
    @XmlElement(name = "MiddleDistance")
    private Distance middleDistance;
    @Element(name = "Radius")
    @XmlElement(name = "Radius")
    private int radius;
    @Element(name = "Temperature")
    @XmlElement(name = "Temperature")
    private int temperature;
    @Element(name = "InventingDate")
    @XmlElement(name = "InventingDate")
    private Date inventingDate; //= DateTime.Now;

    @ElementList
    public final HashSet<Planet> Planets ;//= new HashSet<>();
}
