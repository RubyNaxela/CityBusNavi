package com.rubynaxela.citybusnavi.gui.components.renderers;

import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Time48h;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.StopTime;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;
import com.rubynaxela.citybusnavi.gui.components.ResourceButton;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJLabel;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJPanel;
import com.rubynaxela.citybusnavi.gui.html.HTMLTags;
import com.rubynaxela.citybusnavi.util.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static com.rubynaxela.citybusnavi.util.Utils.gridElementSettings;

/**
 * This class is a renderer for an {@link java.util.ArrayList}
 * of {@link com.rubynaxela.citybusnavi.data.datatypes.gtfs.StopTime}
 * objects. The result is a {@link javax.swing.JPanel} with the times from
 * {@link com.rubynaxela.citybusnavi.data.datatypes.gtfs.StopTime#departureTime}
 * printed as a timetable
 */
public class Timetable extends DefaultJPanel {

    private final ArrayList<Pair<Trip, StopTime>> stopTimes;

    public Timetable(ArrayList<Pair<Trip, StopTime>> stopTimes, Time48h startTime, Time48h endTime) {

        this.stopTimes = stopTimes;

        try {
            int row = 0;
            for (int hour = startTime.get48Hour() - 1; hour <= endTime.get48Hour() + 1; hour++) {

                final DefaultJLabel hourLabel = new DefaultJLabel("" + hour % 24);
                hourLabel.setFontSizeAndStyle(14, Font.BOLD);
                register(hourLabel, gridElementSettings(row, 0));

                int col = 1;
                for (Pair<Trip, StopTime> stopTime : timesMatchingHour(hour)) {

                    final String timeText = timeText(stopTime.getValue().departureTime, stopTime.getKey());
                    final ResourceButton<StopTime> stopTimeButton = new ResourceButton<>(timeText, SwingConstants.LEFT);
                    stopTimeButton.bindResource(stopTime.getValue());
                    stopTimeButton.setFontSize(14);
                    stopTimeButton.setBackground(this.getBackground());
                    stopTimeButton.setBorder(new EmptyBorder(0, 2, 0, 2));
                    register(stopTimeButton, gridElementSettings(row, col));

                    col++;
                }
                if (col == 1) {
                    final DefaultJLabel dashLabel = new DefaultJLabel("-");
                    dashLabel.setFontSize(14);
                    register(dashLabel, gridElementSettings(row, 1));
                }
                row++;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private ArrayList<Pair<Trip, StopTime>> timesMatchingHour(int hour) {
        ArrayList<Pair<Trip, StopTime>> matching = new ArrayList<>();
        for (Pair<Trip, StopTime> stopTime : stopTimes)
            if (stopTime.getValue().departureTime.getTime().getHour() == hour % 24) matching.add(stopTime);
        return matching;
    }

    private String timeText(Time48h departureTime, Trip trip) {
        StringBuilder timeText = new StringBuilder(Utils.intToTwoDigits(departureTime.getTime().getMinute()));
        for (String annotation : trip.getAnnotations())
            if (!annotation.equals("N")) timeText.append(annotation);
        if (trip.wheelchairAccessible)
            timeText = new StringBuilder(HTMLTags.html(HTMLTags.u(timeText.toString())));
        return timeText.toString();
    }
}
