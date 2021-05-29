package com.rubynaxela.citybusnavi.commands;

import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;

import java.util.ArrayList;

public final class DataProcessor {

    private final DatabaseAccessor databaseAccessor;

    public DataProcessor(CityBusNavi instance) {
        databaseAccessor = instance.getDatabaseAccessor();
    }

    public Pair<ArrayList<Trip>, Integer> findMainVariantTrips(Route route) {
        ArrayList<Trip> mainVariants = new ArrayList<>();
        boolean firstFound = false, secondFound = false;
        for (final Trip trip : databaseAccessor.getTripsByRouteId(route.routeId)) {
            if (!firstFound && trip.isMainVariant() && trip.directionId) {
                firstFound = true;
                mainVariants.add(trip);
            }
            if (!secondFound && trip.isMainVariant() && !trip.directionId) {
                secondFound = true;
                mainVariants.add(trip);
            }
        }
        int maxStops = 0;
        for (final Trip trip : mainVariants) {
            final int stops = databaseAccessor.getTripScheduleByTripId(trip.tripId).stopTimes.size();
            if (stops > maxStops) maxStops = stops;
        }
        return new Pair<>(mainVariants, maxStops);
    }
}
