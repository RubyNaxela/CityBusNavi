package com.rubynaxela.citybusnavi.data.datatypes.derived;

import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.GeoPoint;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.ShapePoint;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Shapes defined by points ({@link ShapePoint}) from shapes.csv
 */
public class Shape implements Comparable<Shape>, Serializable {

    public int shapeId;
    public ArrayList<GeoPoint> points;

    public Shape(int shapeId) {
        this.shapeId = shapeId;
        points = new ArrayList<>();
    }

    @Override
    public int compareTo(@NotNull Shape other) {
        return shapeId - other.shapeId;
    }
}
