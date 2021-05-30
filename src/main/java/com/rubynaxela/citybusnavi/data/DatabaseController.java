package com.rubynaxela.citybusnavi.data;

import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.assets.StringManager;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.GeoPoint;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Time48h;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Trilean;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Calendar;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Shape;
import com.rubynaxela.citybusnavi.data.datatypes.derived.TripSchedule;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Zone;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.*;
import com.rubynaxela.citybusnavi.gui.DialogHandler;
import com.rubynaxela.citybusnavi.io.Directory;
import com.rubynaxela.citybusnavi.io.IOHandler;
import com.rubynaxela.citybusnavi.util.CSVParser;
import com.rubynaxela.citybusnavi.util.Utils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

public final class DatabaseController {

    private final StringManager stringManager;
    private final IOHandler ioHandler;
    private final DialogHandler dialogHandler;

    private final Database database;

    public DatabaseController(Database database, CityBusNavi instance) {
        this.database = database;
        stringManager = instance.getStringManager();
        ioHandler = instance.getIOHandler();
        dialogHandler = instance.getDialogHandler();
    }

    /**
     * Loads stops data from a CSV file to the database
     *
     * @param source the stops CSV file
     * @see DatabaseAccessor#getStops
     * @see DatabaseAccessor#getStopById(int)
     */
    public void loadStops(File source) {
        try {
            database.stops = new CSVParser<>(data -> {
                final Stop stop = new Stop();
                stop.stopId = Integer.parseInt(data[0]);
                stop.stopCode = Utils.removeQuotes(data[1]);
                stop.stopName = Utils.removeQuotes(data[2]);
                stop.stopLat = Double.parseDouble(data[3]);
                stop.stopLon = Double.parseDouble(data[4]);
                stop.zone = Zone.getById(data[5]);
                return stop;
            }).parse(source);
            database.stops.sort(Comparator.naturalOrder());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.data_load"), true);
        }
    }

    /**
     * Loads shapes data from a CSV file to the database
     *
     * @param source the shapes CSV file
     * @see DatabaseAccessor#getShapeById
     */
    public void loadShapes(File source) {
        final ArrayList<ShapePoint> shapePoints;
        try {
            shapePoints = new CSVParser<>(data -> {
                final ShapePoint shapePoint = new ShapePoint();
                shapePoint.shapeId = Integer.parseInt(data[0]);
                shapePoint.shapePtLat = Double.parseDouble(data[1]);
                shapePoint.shapePtLon = Double.parseDouble(data[2]);
                shapePoint.shapePtSequence = Integer.parseInt(data[3]);
                return shapePoint;
            }).parse(source);

            final LinkedHashMap<Integer, Shape> shapesMap = new LinkedHashMap<>();
            for (ShapePoint shapePoint : shapePoints) {
                if (!shapesMap.containsKey(shapePoint.shapeId))
                    shapesMap.put(shapePoint.shapeId, new Shape(shapePoint.shapeId));
                shapesMap.get(shapePoint.shapeId).points.add(new GeoPoint(shapePoint.shapePtLat, shapePoint.shapePtLon));
            }
            database.shapes = new ArrayList<>(shapesMap.values());
            database.shapes.sort(Comparator.naturalOrder());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.data_load"), true);
        }
    }

    /**
     * Loads routes data from a CSV file to the database
     *
     * @param source the routes CSV file
     * @see DatabaseAccessor#getRoutes
     */
    public void loadRoutes(File source) {
        try {
            database.routes = new CSVParser<>(data -> {
                final Route route = new Route();
                route.routeId = data[0];
                route.agencyId = Integer.parseInt(data[1]);
                route.routeShortName = Utils.removeQuotes(data[2]);
                route.routeLongName = Utils.removeQuotes(data[3]);
                route.routeDesc = Utils.removeQuotes(data[4]);
                switch (Utils.removeQuotes(data[5])) {
                    case "0":
                        route.routeType = Route.RouteType.TRAM;
                        break;
                    case "3":
                        route.routeType = Route.RouteType.BUS;
                        break;
                    default:
                        route.routeType = null;
                }
                if (route.getGroup() == 1 || route.getGroup() > 2) {
                    route.routeColor = new Color(238, 238, 238);
                    route.routeTextColor = new Color(34, 34, 34);
                } else {
                    route.routeColor = Color.decode("#" + data[6]);
                    route.routeTextColor = Color.decode("#" + data[7]);
                }
                return route;
            }).parse(source);
            database.routes.sort(Comparator.naturalOrder());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.data_load"), true);
        }
    }

    /**
     * Loads trips data from a CSV file to the database
     *
     * @param source the trips CSV file
     * @see DatabaseAccessor#getTrips
     */
    public void loadTrips(File source) {
        try {
            database.trips = new CSVParser<>(data -> {
                final Trip trip = new Trip();
                trip.routeId = data[0];
                trip.serviceId = Integer.parseInt(data[1]);
                trip.tripId = Utils.removeQuotes(data[2]);
                trip.tripHeadsign = Utils.removeQuotes(data[3]);
                trip.directionId = data[4].equals("1");
                trip.shapeId = Integer.parseInt(data[5]);
                trip.wheelchairAccessible = data[6].equals("1");
                return trip;
            }).parse(source);
            database.trips.sort(Comparator.naturalOrder());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.data_load"), true);
        }
    }

    /**
     * Loads stop times data from a CSV file to the database
     *
     * @param source the stop times CSV file
     * @see DatabaseAccessor#getTripSchedules
     * @see DatabaseAccessor#getTripScheduleByTripId(String)
     */
    public void loadTripSchedules(File source) {
        final ArrayList<StopTime> stopTimes;
        try {
            stopTimes = new CSVParser<>(data -> {
                final StopTime stopTime = new StopTime();
                stopTime.tripId = Utils.removeQuotes(data[0]);
                stopTime.arrivalTime = Time48h.parseHMSTime(data[1]);
                stopTime.departureTime = Time48h.parseHMSTime(data[2]);
                stopTime.stopId = Integer.parseInt(data[3]);
                stopTime.stopSequence = Integer.parseInt(data[4]);
                stopTime.stopHeadsign = Utils.removeQuotes(data[5]);
                switch (Utils.removeQuotes(data[6])) {
                    case "0":
                        stopTime.pickupType = Trilean.POSITIVE;
                        break;
                    case "1":
                        stopTime.pickupType = Trilean.NEGATIVE;
                        break;
                    case "3":
                        stopTime.pickupType = Trilean.NEUTRAL;
                        break;
                    default:
                        stopTime.pickupType = null;
                }
                switch (Utils.removeQuotes(data[7])) {
                    case "0":
                        stopTime.dropOffType = Trilean.POSITIVE;
                        break;
                    case "1":
                        stopTime.dropOffType = Trilean.NEGATIVE;
                        break;
                    case "3":
                        stopTime.dropOffType = Trilean.NEUTRAL;
                        break;
                    default:
                        stopTime.dropOffType = null;
                }
                return stopTime;
            }).parse(source);

            final LinkedHashMap<String, TripSchedule> tripSchedulesMap = new LinkedHashMap<>();
            for (StopTime stopTime : stopTimes) {
                if (!tripSchedulesMap.containsKey(stopTime.tripId))
                    tripSchedulesMap.put(stopTime.tripId, new TripSchedule(stopTime.tripId));
                tripSchedulesMap.get(stopTime.tripId).stopTimes.add(stopTime);
            }
            database.tripSchedules = new ArrayList<>(tripSchedulesMap.values());
            database.tripSchedules.sort(Comparator.naturalOrder());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.data_load"), true);
        }
    }

    /**
     * Loads calendar data from a CSV file to the database
     *
     * @param source the calendar CSV file
     * @see DatabaseAccessor#getCalendar
     */
    public void loadCalendar(File source) {
        ArrayList<CalendarEntry> calendarEntries;
        try {
            calendarEntries = new CSVParser<>(data -> {
                final CalendarEntry entry = new CalendarEntry();
                entry.serviceId = Integer.parseInt(data[0]);
                entry.monday = data[1].equals("1");
                entry.tuesday = data[2].equals("1");
                entry.wednesday = data[3].equals("1");
                entry.thursday = data[4].equals("1");
                entry.friday = data[5].equals("1");
                entry.saturday = data[6].equals("1");
                entry.sunday = data[7].equals("1");
                entry.startDate = Utils.parseDate(data[8]);
                entry.endDate = Utils.parseDate(data[9]);
                return entry;
            }).parse(source);
            database.calendar = new Calendar(calendarEntries);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.data_load"), true);
        }
    }

    /**
     * Loads all database entries from the CSV data files
     */
    public void loadAll() {
        final Directory dataDirectory = new Directory(stringManager.get("string.gtfs_file.directory"));
        final File stopsFile = new File(dataDirectory, "stops.csv"),
                shapesFile = new File(dataDirectory, "shapes.csv"),
                routesFile = new File(dataDirectory, "routes.csv"),
                tripsFile = new File(dataDirectory, "trips.csv"),
                tripSchedulesFile = new File(dataDirectory, "stop_times.csv"),
                calendarFile = new File(dataDirectory, "calendar.csv");
        if (!stopsFile.exists() || !shapesFile.exists() || !routesFile.exists() ||
                !tripsFile.exists() || !tripSchedulesFile.exists() || !calendarFile.exists()) {
            try {
                ioHandler.retrieveGTFSData(new URL(stringManager.get("string.gtfs_file.url")),
                        new File(stringManager.get("string.gtfs_file.directory"), stringManager.get("string.gtfs_file.name")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                dialogHandler.showError(stringManager.get("lang.error.server_connection"));
            }
        }
        loadStops(stopsFile);
        loadShapes(shapesFile);
        loadRoutes(routesFile);
        loadTrips(tripsFile);
        loadTripSchedules(tripSchedulesFile);
        loadCalendar(calendarFile);
    }
}
