package com.it.doubledi.cinemamanager._common.util;

import java.time.Instant;
import java.time.ZoneId;
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
        Date date = Date.from(time);
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31, 0, 0, 0);
        return calendar.toInstant();
    }

    public static Instant getFirstDayOfMonth(Instant time) {
        Date date = Date.from(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 23, 59, 59);
        return calendar.toInstant().atZone(ZoneId.systemDefault()).toInstant();
    }

    public static Instant getLastDayOfMonth(Instant time) {
        Date date = Date.from(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1, 23, 59, 59);
        return calendar.toInstant();
    }
}
