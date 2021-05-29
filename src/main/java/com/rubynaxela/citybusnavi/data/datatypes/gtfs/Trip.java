package com.rubynaxela.citybusnavi.data.datatypes.gtfs;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Entires from trips.csv
 */
public class Trip implements Comparable<Trip>, Serializable {

    public String routeId, tripId, tripHeadsign;
    public int serviceId, shapeId;
    public boolean directionId, wheelchairAccessible;

    public boolean isMainVariant() {
        return tripId.endsWith("+");
    }

    public ArrayList<String> getAnnotations() {

        final ArrayList<String> annotations = new ArrayList<>();

        if (tripId.contains("^")) {
            String annotationsSection = tripId.split("\\^")[1];
            annotationsSection = annotationsSection.replace("+", "").replace("N", "");
            if (!annotationsSection.isEmpty()) annotations.addAll(Arrays.asList(annotationsSection.split(",")));
        }

        return annotations;
    }

    public String getFullName() {
        return routeId + " -> " + tripHeadsign + " #" + tripId;
    }

    public int getRouteGroup() {
        try {
            return Integer.parseInt(routeId) / 100;
        } catch (NumberFormatException ignored) {
            return 10;
        }
    }

    @Override
    public int compareTo(@NotNull Trip other) {
        return tripId.compareTo(other.tripId);
    }
}