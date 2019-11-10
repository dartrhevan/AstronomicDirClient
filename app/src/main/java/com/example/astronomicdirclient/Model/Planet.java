package com.example.astronomicdirclient.Model;

import org.joda.time.DateTime;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;


@Root(name = "Planet")
@Default(DefaultType.FIELD)
public class Planet implements ISpaceObject, Serializable, Cloneable
{

    @Override
    public Object clone() throws CloneNotSupportedException {
        Planet clone = (Planet)super.clone();
        clone.inventingDate = new DateTime(inventingDate);
        clone.middleDistance = (Distance) middleDistance.clone();
        clone.Moons = (HashSet<Moon>) Moons.clone();
        return clone;
    }

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

    public Planet(byte[] photo, String name, Distance middleDistance, int radius, int temperature, DateTime inventingDate, String star, boolean hasAtmosphere, PlanetType type, HashSet<Moon> moons, String galaxy) {
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

    public Planet(byte[] photo, String name, Distance middleDistance, int radius, int temperature, DateTime inventingDate, String star, boolean hasAtmosphere, PlanetType type, String galaxy) {
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
    protected String name;
    @Element(name = "MiddleDistance", required=false)
    protected Distance middleDistance = new Distance();
    @Element(name = "Radius")
    protected int radius;
    @Element(name = "Temperature")
    protected int temperature;
    @Element(name = "InventingDate", required=false)
    protected DateTime inventingDate; //= DateTime.Now;

    @Element(name = "Photo", required=false)
    protected byte[] photo;
    @Element(name = "Star", required = false)
    protected String star;
    @Element(name = "HasAtmosphere")
    protected boolean hasAtmosphere;

    @Element(name = "Type")
    protected PlanetType type;
    private HashSet<Moon> Moons;//= new HashSet<>();

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

    public DateTime getInventingDate() {
        return inventingDate;
    }

    public void setInventingDate(DateTime inventingDate) {
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
    @Element(name = "Galaxy", required = false)
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
