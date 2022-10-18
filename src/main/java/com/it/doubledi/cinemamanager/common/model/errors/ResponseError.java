package com.it.doubledi.cinemamanager.common.model.errors;

public interface ResponseError {
    String getName();

    String getMessage();

    int getStatus();

    default Integer getCode() {
        return 0;
    }

}
