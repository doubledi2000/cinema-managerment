package com.it.doubledi.cinemamanager.infrastructure.support.errors;

import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    TYPE_OF_FILM_CODE_ALREADY_EXISTED(40400001, "Type of film code existed"),
    DURATION_OF_FILM_REQUIRED(40400003, "Duration of film is required"),
    FILM_ALREADY_GEN_TICKET(40040003, "Film already generate ticket"),
    FILM_MUST_CONTAIN_TYPE(40040004, "Film must contain type"),
    ;


    private final Integer code;
    private final String message;

    BadRequestError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getName() {
        return name();
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
