package com.rubynaxela.citybusnavi.gui.components.renderers;

import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Time48h;
import com.rubynaxela.citybusnavi.data.datatypes.derived.TripSchedule;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Stop;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.StopTime;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;
import com.rubynaxela.citybusnavi.gui.components.ResourceButton;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJLabel;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJPanel;
import com.rubynaxela.citybusnavi.util.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static com.rubynaxela.citybusnavi.util.Utils.gridElementSettings;

/**
 * This class is a renderer for a {@link com.rubynaxela.citybusnavi.data.datatypes.derived.TripSchedule} object.
 * The result is a {@link javax.swing.JPanel} with the stops names obtained from
 * {@link com.rubynaxela.citybusnavi.data.datatypes.derived.TripSchedule#stopTimes}
 * printed as a list with time offsets
 */
public class StopsList extends DefaultJPanel {

    public final DefaultJLabel directionLabel = new DefaultJLabel();
    public final ArrayList<ResourceButton<Stop>> stopButtons = new ArrayList<>();

    public StopsList(Trip trip, int maxStops, DatabaseAccessor databaseAccessor) {

        directionLabel.setFontSizeAndStyle(14, Font.BOLD);
        directionLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        register(directionLabel, gridElementSettings(0, 0));

        final DefaultJPanel list = new DefaultJPanel();
        register(list, gridElementSettings(1, 0));

        final TripSchedule tripSchedule = databaseAccessor.getTripScheduleByTripId(trip.tripId);

        int i = 0;
        StopTime first = tripSchedule.stopTimes.get(0);
        for (StopTime stopTime : tripSchedule.stopTimes) {

            final Stop stop = databaseAccessor.getStopById(stopTime.stopId);

            final DefaultJLabel offsetLabel = new DefaultJLabel((Time48h.difference(
                    first.departureTime, stopTime.departureTime).getSeconds() / 60) + "'", SwingConstants.RIGHT);
            offsetLabel.setFontSize(9);
            list.register(offsetLabel, gridElementSettings(i, 0));

            final ResourceButton<Stop> stopButton = new ResourceButton<>(stop.stopName, SwingConstants.LEFT);
            stopButton.bindResource(stop);
            stopButton.setFontSizeAndStyle(9, Font.BOLD);
            stopButton.setBorder(new EmptyBorder(0, 5, 0, 5));
            list.register(stopButton, Utils.gridElementSettings(i, 2));
            stopButtons.add(stopButton);

            final DefaultJLabel zoneLabel = new DefaultJLabel(stop.zone.getId());
            zoneLabel.setFontSizeAndStyle(9, Font.BOLD);
            zoneLabel.setForeground(Color.WHITE);
            zoneLabel.setBackground(stop.zone.getColor());
            list.register(zoneLabel, gridElementSettings(i, 3));

            i++;
        }
        for (; i < maxStops; i++) {
            final DefaultJLabel emptyLabel = new DefaultJLabel(" ");
            emptyLabel.setFontSize(9);
            list.register(emptyLabel, gridElementSettings(i, 0));
        }
    }
}
