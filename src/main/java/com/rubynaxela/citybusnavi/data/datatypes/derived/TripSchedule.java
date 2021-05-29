package com.rubynaxela.citybusnavi.data.datatypes.derived;

import com.rubynaxela.citybusnavi.data.datatypes.gtfs.StopTime;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class TripSchedule implements Comparable<TripSchedule>, Serializable {

    public String tripId;
    public ArrayList<StopTime> stopTimes;

    public TripSchedule(String tripId) {
        this.tripId = tripId;
        stopTimes = new ArrayList<>();
    }

    @Nullable
    public StopTime getStopTimeByStopId(int stopId) {
        for (StopTime stopTime : stopTimes) if (stopTime.stopId == stopId) return stopTime;
        return null;
    }

    @Override
    public int compareTo(@NotNull TripSchedule other) {
        return tripId.compareTo(other.tripId);
    }
}
