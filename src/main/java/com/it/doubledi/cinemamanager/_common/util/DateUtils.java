package com.it.doubledi.cinemamanager._common.util;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
    }

    public static Instant getFirstDayOfYear(Instant time) {
        Date date = Date.from(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
        return calendar.toInstant();
    }

    public static Instant getLastDayOfYear(Instant time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(time));
        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31, 0, 0, 0);
        return calendar.toInstant();
    }
}
