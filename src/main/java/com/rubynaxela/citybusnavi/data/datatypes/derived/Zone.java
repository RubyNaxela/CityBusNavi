package com.rubynaxela.citybusnavi.data.datatypes.derived;

import java.awt.*;

public enum Zone {
    A("A"), B("B"), C("C"), D("D"), PR("PR"), UNKNOWN("?");

    private final String zoneId;

    Zone(String zoneId) {
        this.zoneId = zoneId;
    }

    public static Zone getById(String zoneId) {
        for (Zone zone : Zone.values()) if (zone.getId().equals(zoneId)) return zone;
        return UNKNOWN;
    }

    public String getId() {
        return zoneId;
    }

    public Color getColor() {
        switch (zoneId) {
            case "A":
                return new Color(59, 135, 81);
            case "B":
                return new Color(173, 38, 34);
            case "C":
                return new Color(184, 185, 57);
            case "D":
                return new Color(34, 116, 197);
            default:
                return Color.BLACK;
        }
    }
}
