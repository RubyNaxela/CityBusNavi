package com.rubynaxela.citybusnavi.data.datatypes.derived;

import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.WeekDay;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.CalendarEntry;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rubynaxela.citybusnavi.data.datatypes.auxiliary.WeekDay.*;

public class Calendar implements Serializable {

    private final HashMap<WeekDay, Integer> services;

    public Calendar(ArrayList<CalendarEntry> calendarEntries) {
        services = new HashMap<>();
        for (CalendarEntry entry : calendarEntries) {
            if (entry.monday) services.put(MONDAY, entry.serviceId);
            if (entry.tuesday) services.put(TUESDAY, entry.serviceId);
            if (entry.wednesday) services.put(WEDNESDAY, entry.serviceId);
            if (entry.thursday) services.put(THURSDAY, entry.serviceId);
            if (entry.friday) services.put(FRIDAY, entry.serviceId);
            if (entry.saturday) services.put(SATURDAY, entry.serviceId);
            if (entry.sunday) services.put(SUNDAY, entry.serviceId);
        }
    }

    /**
     * Returns the service id scheduled for the specified day of the week
     *
     * @param day a day of the week
     * @return the service id
     */
    @Contract(pure = true)
    public int getServiceId(WeekDay day) {
        return services.get(day);
    }
}
