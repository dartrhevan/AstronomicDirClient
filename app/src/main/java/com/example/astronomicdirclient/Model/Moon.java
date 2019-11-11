package com.example.astronomicdirclient.Model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Moon")
@Default(DefaultType.FIELD)
public class Moon extends Planet {

    public String getPlanetOwner() {
        return planetOwner;
    }

    public void setPlanetOwner(String planetOwner) {
        this.planetOwner = planetOwner;
    }

    @Element(name = "PlanetOwner", required = false)
    private String planetOwner;
    public Moon(){
        type = PlanetType.Moon;
    }

    public Moon(String name, Planet planetOwner) //: base(planetOwner.star, planetOwner.galaxy)
    {
        super(name, planetOwner.star, planetOwner.galaxy);
        this.planetOwner = planetOwner.name;
        middleDistance = planetOwner.middleDistance;
        type = PlanetType.Moon;

    }
}
