package com.it.doubledi.cinemamanager.infrastructure.support.errors;

import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;

public enum NotFoundError implements ResponseError {
    ROOM_NOT_FOUND(4040001, "Room not found"),
    ROW_NOT_FOUND(4040002, "row not found"),
    CHAIR_NOT_FOUND(4040003, "chair not found"),
    ;
    private final Integer code;
    private final String message;

    NotFoundError(Integer code, String message) {
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
