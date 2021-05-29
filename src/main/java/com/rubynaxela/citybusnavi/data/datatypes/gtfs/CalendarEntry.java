package com.rubynaxela.citybusnavi.data.datatypes.gtfs;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entries from calendar.csv
 */
public class CalendarEntry implements Serializable {
    public int serviceId;
    public boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    public LocalDate startDate, endDate;
}
