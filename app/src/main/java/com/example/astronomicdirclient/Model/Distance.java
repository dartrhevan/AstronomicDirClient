package com.example.astronomicdirclient.Model;

import java.util.Objects;

public class Distance {
    public Distance(int value, UnitType unit) {
        Value = value;
        Unit = unit;
    }

    public final int Value;
    /// <summary>
    /// Единица Измерения
    /// </summary>
    public final UnitType Unit;

    @Override
    public String toString() {
        return "Distance{" +
                "Value=" + Value +
                ", Unit=" + Unit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return Value == distance.Value &&
                Unit == distance.Unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Value, Unit);
    }
}
