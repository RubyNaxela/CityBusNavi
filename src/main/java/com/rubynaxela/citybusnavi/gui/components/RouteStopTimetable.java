package com.rubynaxela.citybusnavi.gui.components;

import com.rubynaxela.citybusnavi.assets.StringManager;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Time48h;
import com.rubynaxela.citybusnavi.data.datatypes.derived.RouteSchedule;
import com.rubynaxela.citybusnavi.gui.components.renderers.Timetable;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJFrame;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJLabel;

import static com.rubynaxela.citybusnavi.util.Utils.gridElementSettings;

public final class RouteStopTimetable extends DefaultJFrame {

    public RouteStopTimetable(RouteSchedule routeSchedule, StringManager stringManager) {

        super(false);

        boolean isDayLine = routeSchedule.getRoute().getGroup() != 2;

        register(new DefaultJLabel(isDayLine ?
                stringManager.get("lang.label.workdays") : stringManager.get("lang.label.other_nights")
        ), gridElementSettings(0, 0));
        register(new DefaultJLabel(isDayLine ?
                stringManager.get("lang.label.saturdays") : stringManager.get("lang.label.saturday_sunday_nights")
        ), gridElementSettings(0, 1));
        register(new DefaultJLabel(isDayLine ?
                stringManager.get("lang.label.sundays") : stringManager.get("lang.label.sunday_monday_nights")
        ), gridElementSettings(0, 2));

        final Time48h earliest = routeSchedule.getEarliestTime(), latest = routeSchedule.getLatestTime();

        register(new Timetable(routeSchedule.getTuesdayColumn(), earliest, latest), gridElementSettings(1, 0));
        register(new Timetable(routeSchedule.getSaturdayColumn(), earliest, latest), gridElementSettings(1, 1));
        register(new Timetable(routeSchedule.getSundayColumn(), earliest, latest), gridElementSettings(1, 2));

        pack();
    }
}
