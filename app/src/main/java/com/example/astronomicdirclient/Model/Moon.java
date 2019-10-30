package com.example.astronomicdirclient.Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Moon extends Planet {
    @XmlElement(name = "PlanetOwner")
    private String planetOwner;
    public Moon(){
        type = PlanetType.Moon;
    }

    public Moon(Planet planetOwner) //: base(planetOwner.star, planetOwner.galaxy)
    {
        super(planetOwner.star, planetOwner.galaxy);
        this.planetOwner = planetOwner.name;
        middleDistance = planetOwner.middleDistance;
        //this.Moons = null;
        type = PlanetType.Moon;

    }
    /*public Moon(byte[] photo, String name, Distance middleDistance, int radius, int temperature, Date inventingDate, String star, boolean hasAtmosphere, PlanetType type) {
        super(photo, name, middleDistance, radius, temperature, inventingDate, star, hasAtmosphere, type);
    }*/
}
