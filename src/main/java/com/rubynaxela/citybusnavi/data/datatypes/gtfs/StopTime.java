package com.rubynaxela.citybusnavi.data.datatypes.gtfs;

import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Time48h;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Trilean;

import java.io.Serializable;

/**
 * Entires from stop_times.csv
 */
public class StopTime implements Serializable {
    public String tripId, stopHeadsign;
    public Time48h arrivalTime, departureTime;
    public int stopId, stopSequence;
    public Trilean pickupType, dropOffType;
}
