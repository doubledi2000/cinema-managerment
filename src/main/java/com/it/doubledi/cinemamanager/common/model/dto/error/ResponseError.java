package com.it.doubledi.cinemamanager.common.model.dto.error;

public interface ResponseError {

    String getName();

    String getMessage();

    int getStatus();

    default Integer getCode() {
        return 0;
    }
}
