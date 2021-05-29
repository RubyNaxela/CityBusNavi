package com.rubynaxela.citybusnavi.data.datatypes.gtfs;

import java.io.Serializable;

/**
 * Entries from shapes.csv
 */
public class ShapePoint implements Serializable {
    public int shapeId, shapePtSequence;
    public double shapePtLat, shapePtLon;
}
