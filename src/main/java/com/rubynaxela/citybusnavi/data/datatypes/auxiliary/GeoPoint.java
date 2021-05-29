package com.rubynaxela.citybusnavi.data.datatypes.auxiliary;

import java.io.Serializable;

/**
 * Point in the world denoted by geographical coordinates
 */
public class GeoPoint implements Serializable {
    public double latitude, longitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
