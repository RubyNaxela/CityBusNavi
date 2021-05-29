package com.rubynaxela.citybusnavi.data;

import com.rubynaxela.citybusnavi.data.datatypes.derived.Calendar;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Shape;
import com.rubynaxela.citybusnavi.data.datatypes.derived.TripSchedule;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Stop;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;

import java.util.ArrayList;
import java.util.Collections;

public final class DatabaseAccessor {

    private final Database database;

    public DatabaseAccessor(Database database) {
        this.database = database;
    }

    public ArrayList<Stop> getStops() {
        return database.stops;
    }

    public Stop getStopById(int stopId) {
        final Stop searched = new Stop();
        searched.stopId = stopId;
        return database.stops.get(Collections.binarySearch(database.stops, searched));
    }

    public Shape getShapeById(int shapeId) {
        return database.shapes.get(Collections.binarySearch(database.shapes, new Shape(shapeId)));
    }

    public ArrayList<Route> getRoutes() {
        return database.routes;
    }

    public ArrayList<Trip> getTrips() {
        return database.trips;
    }

    public ArrayList<Trip> getTripsByRouteId(String routeId) {
        final ArrayList<Trip> trips = new ArrayList<>();
        for (final Trip trip : getTrips()) if (trip.routeId.equals(routeId)) trips.add(trip);
        return trips;
    }

    public ArrayList<TripSchedule> getTripSchedules() {
        return database.tripSchedules;
    }

    public TripSchedule getTripScheduleByTripId(String tripId) {
        return database.tripSchedules.get(Collections.binarySearch(database.tripSchedules, new TripSchedule(tripId)));
    }

    public Calendar getCalendar() {
        return database.calendar;
    }
}
