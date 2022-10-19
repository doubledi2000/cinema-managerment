package com.it.doubledi.cinemamanager._common.model.dto.error;

public interface ResponseError {

    String getName();

    String getMessage();

    int getStatus();

    default Integer getCode() {
        return 0;
    }
}
