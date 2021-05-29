package com.rubynaxela.citybusnavi.data.datatypes.auxiliary;

import com.rubynaxela.citybusnavi.util.Utils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

/**
 * This is a class to store the time and whether it's the time in the next day
 */
public final class Time48h implements Comparable<Time48h>, Serializable {

    private final LocalTime time;
    private final boolean tomorrow;

    public Time48h(LocalTime time, boolean tomorrow) {
        this.time = time;
        this.tomorrow = tomorrow;
    }

    /**
     * Parses a {@link String} containing 24-hour system time formatted
     * as hh:mm:ss to a time point. If the hour number is greater than 23,
     * it's reduced by 24 and the time is marked as the next day
     *
     * @param time the time stored as a {@link String}
     * @return a {@link Time48h} object
     */
    @Contract(pure = true)
    public static Time48h parseHMSTime(@NotNull String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        if (hour < 24) return new Time48h(LocalTime.parse(time), false);
        else return new Time48h(LocalTime.parse(Utils.intToTwoDigits(hour - 24) + time.substring(2)), true);
    }

    /**
     * Calculates the difference between two {@link Time48h} time points. If the second
     * parameter time is earlier than the first parameter time, the result will be negative
     *
     * @param earlierTime the time point expected to be earlier
     * @param laterTime   the time point expected to be later
     * @return a {@link java.time.Duration} object storing the difference
     */
    @Contract(pure = true)
    public static Duration difference(Time48h earlierTime, Time48h laterTime) {
        if (laterTime.tomorrow == earlierTime.tomorrow)
            return Duration.between(earlierTime.time, laterTime.time);
        else
            return Duration.between(LocalTime.MIDNIGHT, laterTime.time)
                    .plus(Duration.between(earlierTime.time, LocalTime.NOON)).plusHours(12);
    }

    public LocalTime getTime() {
        return time;
    }

    public int get48Hour() {
        return time.getHour() + (tomorrow ? 24 : 0);
    }

    public boolean isTomorrow() {
        return tomorrow;
    }

    @Override
    public int compareTo(@NotNull Time48h other) {
        if (!isTomorrow() && other.isTomorrow()) return -1;
        else if (isTomorrow() && !other.isTomorrow()) return 1;
        else return getTime().compareTo(other.getTime());
    }
}
