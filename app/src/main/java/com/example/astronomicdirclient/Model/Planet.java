package com.example.astronomicdirclient.Model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Root(name = "Planet")
@Default(DefaultType.FIELD)
@XmlRootElement
public class Planet implements ISpaceObject, Serializable
{

    @Override
    public String toString() {
        return name + '(' + star +')';/*"Planet{" +
                "photo=" + Arrays.toString(photo) +
                ", name='" + name + '\'' +
                ", middleDistance=" + middleDistance +
                ", radius=" + radius +
                ", temperature=" + temperature +
                ", inventingDate=" + inventingDate +
                ", star='" + star + '\'' +
                ", hasAtmosphere=" + hasAtmosphere +
                ", type=" + type +
                ", Moons=" + Moons +
                '}';*/
    }

    public Planet(byte[] photo, String name, Distance middleDistance, int radius, int temperature, Date inventingDate, String star, boolean hasAtmosphere, PlanetType type, HashSet<Moon> moons, String galaxy) {
        this.photo = photo;
        this.name = name;
        this.middleDistance = middleDistance;
        this.radius = radius;
        this.temperature = temperature;
        this.inventingDate = inventingDate;
        this.star = star;
        this.hasAtmosphere = hasAtmosphere;
        this.type = type;
        Moons = moons;
        this.galaxy = galaxy;
    }

    public Planet(byte[] photo, String name, Distance middleDistance, int radius, int temperature, Date inventingDate, String star, boolean hasAtmosphere, PlanetType type, String galaxy) {
        this.photo = photo;
        this.name = name;
        this.galaxy = galaxy;
        this.middleDistance = middleDistance;
        this.radius = radius;
        this.temperature = temperature;
        this.inventingDate = inventingDate;
        this.star = star;
        this.hasAtmosphere = hasAtmosphere;
        this.type = type;
        Moons = new HashSet<>();
    }

    @Element(name = "Name")
    @XmlElement(name = "Name")
    protected String name;
    @Element(name = "MiddleDistance")
    @XmlElement(name = "MiddleDistance")
    protected Distance middleDistance;
    @Element(name = "Radius")
    @XmlElement(name = "Radius")
    protected int radius;
    @Element(name = "Temperature")
    @XmlElement(name = "Temperature")
    protected int temperature;
    @Element(name = "InventingDate")
    @XmlElement(name = "InventingDate")
    protected Date inventingDate; //= DateTime.Now;

    @Element(name = "Photo")
    @XmlElement(name = "Photo")
    protected byte[] photo;
    @Element(name = "Star")
    @XmlElement(name = "Star")
    protected String star;
    @Element(name = "HasAtmosphere")
    @XmlElement(name = "HasAtmosphere")
    protected boolean hasAtmosphere;

    @Element(name = "Type")
    @XmlElement(name = "Type")
    protected PlanetType type;
    public final HashSet<Moon> Moons;//= new HashSet<>();

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Distance getMiddleDistance() {
        return middleDistance;
    }

    public void setMiddleDistance(Distance middleDistance) {
        this.middleDistance = middleDistance;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Date getInventingDate() {
        return inventingDate;
    }

    public void setInventingDate(Date inventingDate) {
        this.inventingDate = inventingDate;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public boolean isHasAtmosphere() {
        return hasAtmosphere;
    }

    public void setHasAtmosphere(boolean hasAtmosphere) {
        this.hasAtmosphere = hasAtmosphere;
    }

    public PlanetType getType() {
        return type;
    }

    public void setType(PlanetType type) {
        this.type = type;
    }
    public Planet(){Moons = new HashSet<>();}


    public Planet(String name, String star, String galaxy){
        this.name = name;
        Moons = new HashSet<>();
        this.star = star;
        this.galaxy = galaxy;
    }
    @Element(name = "Galaxy")
    @XmlElement(name = "Galaxy")
    protected String galaxy;

    public HashSet<Moon> getMoons() {
        return Moons;
    }

    public String getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return radius == planet.radius &&
                temperature == planet.temperature &&
                hasAtmosphere == planet.hasAtmosphere &&
                Arrays.equals(photo, planet.photo) &&
                Objects.equals(name, planet.name) &&
                Objects.equals(middleDistance, planet.middleDistance) &&
                Objects.equals(inventingDate, planet.inventingDate) &&
                Objects.equals(star, planet.star) &&
                type == planet.type &&
                Objects.equals(Moons, planet.Moons);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, middleDistance, radius, temperature, inventingDate, star, hasAtmosphere, type, Moons);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
