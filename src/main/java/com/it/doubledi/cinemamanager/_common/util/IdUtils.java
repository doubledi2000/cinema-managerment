package com.it.doubledi.cinemamanager._common.util;

import java.util.UUID;

public class IdUtils {
    public static String nextId(){
        return UUID.randomUUID().toString();
    }

}
