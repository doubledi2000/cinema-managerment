package com.it.doubledi.cinemamanager.infrastructure.support.errors;

import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;

public enum NotFoundError implements ResponseError {
    ROOM_NOT_FOUND(4040001, "Room not found"),
    ROW_NOT_FOUND(4040002, "row not found"),
    CHAIR_NOT_FOUND(4040003, "chair not found"),
    TYPE_OF_FILM_NOT_FOUND(4040004, "chair not found"),
    PRICE_CONFIG_NOT_FOUND(4040004, "price config not found"),
    PRICE_BY_TIME_NOT_FOUND(4040004, "price by time not found"),
    LOCATION_NOT_FOUND(40040004, "Location not found"),
    FILM_NOT_FOUND(40040004, "Film not found"),
    SHOWTIME_NOT_FOUND(40040005, "Showtime not found"),
    PRODUCER_NOT_FOUND(40040006, "Producer not found"),
    USER_NOT_FOUND(40040007, "User not found"),
    TICKET_NOT_FOUND(40040007, "Ticket not found"),
    ROLE_NOT_FOUND(40040008, "Role not found"),
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
