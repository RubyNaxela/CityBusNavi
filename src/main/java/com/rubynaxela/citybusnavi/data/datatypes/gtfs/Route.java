package com.rubynaxela.citybusnavi.data.datatypes.gtfs;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Serializable;

/**
 * Entries from routes.csv
 */
public class Route implements Comparable<Route>, Serializable {

    public String routeId, routeShortName, routeLongName, routeDesc;
    public int agencyId;
    public RouteType routeType;
    public Color routeColor, routeTextColor;

    public int getGroup() {
        try {
            return Integer.parseInt(routeId) / 100;
        } catch (NumberFormatException ignored) {
            return 10;
        }
    }

    @Override
    public int compareTo(@NotNull Route other) {
        try {
            return Integer.parseInt(routeId) - Integer.parseInt(other.routeId);
        } catch (NumberFormatException ignored) {
            return routeId.compareTo(other.routeId);
        }
    }

    public enum RouteType implements Serializable {
        TRAM, BUS
    }
}
