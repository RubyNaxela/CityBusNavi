package com.rubynaxela.citybusnavi.data.datatypes.gtfs;

import com.rubynaxela.citybusnavi.data.datatypes.derived.Zone;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Entries from stops.csv
 */
public class Stop implements Comparable<Stop>, Serializable {

    public int stopId;
    public String stopCode, stopName;
    public double stopLat, stopLon;
    public Zone zone;

    public String getFullName() {
        return stopName + " [" + stopCode + "]";
    }

    @Override
    public int compareTo(@NotNull Stop other) {
        return stopId - other.stopId;
    }
}
