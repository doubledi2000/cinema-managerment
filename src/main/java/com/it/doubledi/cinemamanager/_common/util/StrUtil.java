package com.it.doubledi.cinemamanager._common.util;

public class StrUtil {
    public static final String EMPTY = "";

    public static String getString(String value) {
        return getString(value, EMPTY);
    }

    public static String getString(String value, String defaultValue) {
        return get(value, defaultValue);
    }

    public static String get(String value, String defaultValue) {
        if (value != null) {
            value = value.trim();

            return value;
        }

        return defaultValue;
    }
}
