package com.rubynaxela.citybusnavi.data;

import com.rubynaxela.citybusnavi.data.datatypes.derived.Calendar;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Shape;
import com.rubynaxela.citybusnavi.data.datatypes.derived.TripSchedule;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.*;

import java.util.ArrayList;

public final class Database {
    public ArrayList<Stop> stops;
    public ArrayList<Shape> shapes;
    public ArrayList<Route> routes;
    public ArrayList<Trip> trips;
    public ArrayList<TripSchedule> tripSchedules;
    public Calendar calendar;
}
