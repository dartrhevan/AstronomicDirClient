package com.example.astronomicdirclient.Model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;


@Root(name = "Distance")
@Default(DefaultType.FIELD)
public class Distance implements Serializable, Cloneable {
    public Distance() {
    }

    public Distance(int value, UnitType unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Element(name = "Value", required = false)
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public UnitType getUnit() {
        return unit;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    @Element(name = "Unit", required = false)
    private UnitType unit;

    @Override
    public String toString() {
        return "Distance{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return value == distance.value &&
                unit == distance.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}
