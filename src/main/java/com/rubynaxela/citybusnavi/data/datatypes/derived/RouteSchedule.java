package com.rubynaxela.citybusnavi.data.datatypes.derived;

import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Time48h;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.WeekDay;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Stop;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.StopTime;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RouteSchedule implements Serializable {

    private final Route route;
    private final Stop stop;

    private final DatabaseAccessor databaseAccessor;

    private final Time48h earliestTime, latestTime;

    public RouteSchedule(Route route, Stop stop, DatabaseAccessor databaseAccessor) {

        this.route = route;
        this.stop = stop;
        this.databaseAccessor = databaseAccessor;

        final ArrayList<Pair<Trip, StopTime>> allDeparatures = new ArrayList<>();
        allDeparatures.addAll(getTuesdayColumn());
        allDeparatures.addAll(getSaturdayColumn());
        allDeparatures.addAll(getSundayColumn());

        final Comparator<Pair<Trip, StopTime>> comparator = Comparator.comparing(t -> t.getValue().departureTime);
        this.earliestTime = Collections.min(allDeparatures, comparator).getValue().departureTime;
        this.latestTime = Collections.max(allDeparatures, comparator).getValue().departureTime;
    }

    private ArrayList<Pair<Trip, StopTime>> getColumn(WeekDay weekDay) {
        final ArrayList<Pair<Trip, StopTime>> times = new ArrayList<>();
        for (Trip trip : databaseAccessor.getTripsByRouteId(route.routeId)) {
            if (trip.serviceId == databaseAccessor.getCalendar().getServiceId(weekDay)) {
                TripSchedule tripSchedule = databaseAccessor.getTripScheduleByTripId(trip.tripId);
                final StopTime stopTime = tripSchedule.getStopTimeByStopId(stop.stopId);
                if (stopTime != null) times.add(new Pair<>(trip, stopTime));
            }
        }
        times.sort(Comparator.comparing(t -> t.getValue().departureTime));
        return times;
    }

    public Route getRoute() {
        return route;
    }

    public Time48h getEarliestTime() {
        return earliestTime;
    }

    public Time48h getLatestTime() {
        return latestTime;
    }

    public ArrayList<Pair<Trip, StopTime>> getTuesdayColumn() {
        return getColumn(WeekDay.TUESDAY);
    }

    public ArrayList<Pair<Trip, StopTime>> getSaturdayColumn() {
        return getColumn(WeekDay.SATURDAY);
    }

    public ArrayList<Pair<Trip, StopTime>> getSundayColumn() {
        return getColumn(WeekDay.SUNDAY);
    }
}
