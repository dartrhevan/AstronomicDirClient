package com.example.astronomicdirclient.Model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Root(name = "StarLite")
@Default(DefaultType.FIELD)
@XmlRootElement(name = "StarLite")
public class StarLite implements Serializable {
    @Element(name = "Id")
    @XmlElement(name = "Id")
    private int id;
    @Element(name = "Name")
    @XmlElement(name = "Name")
    private String name;
    @Element(name = "Galaxy")
    @XmlElement(name = "Galaxy")
    private String galaxy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    public StarLite(int id, String name, String galaxy) {
        this.id = id;
        this.name = name;
        this.galaxy = galaxy;
    }

    @Override
    public String toString() {
        return  name + "(galaxy " + galaxy  + ')';
    }

    public StarLite(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarLite starLite = (StarLite) o;
        return id == starLite.id &&
                Objects.equals(name, starLite.name) &&
                Objects.equals(galaxy, starLite.galaxy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, galaxy);
    }
}
