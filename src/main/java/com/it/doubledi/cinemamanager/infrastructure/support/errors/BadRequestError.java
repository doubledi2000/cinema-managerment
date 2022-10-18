package com.it.doubledi.cinemamanager.infrastructure.support.errors;

import com.it.doubledi.cinemamanager.common.model.errors.ResponseError;
import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
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
