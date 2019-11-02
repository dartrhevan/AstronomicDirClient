package com.example.astronomicdirclient.Model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Root(name = "Moon")
@Default(DefaultType.FIELD)
@XmlRootElement
public class Moon extends Planet {

    @Element(name = "PlanetOwner")
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
