package com.it.doubledi.cinemamanager.infrastructure.support.errors;

import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    TYPE_OF_FILM_CODE_ALREADY_EXISTED(40400001, "Type of film code existed"),
    DURATION_OF_FILM_REQUIRED(40400003, "Duration of film is required"),
    FILM_ALREADY_GEN_TICKET(40040003, "Film already generate ticket"),
    FILM_MUST_CONTAIN_TYPE(40040004, "Film must contain type"),
    ROOM_ALREADY_SCHEDULED(40400005, "Room already have been scheduled"),
    NO_FILM_IN_SHOWTIME_CREATE_LIST(40400006, "No film found in film scheduled list"),
    FILM_WITH_ID_NOT_FOUND(40400007, "Film with id %s not found"),
    FILM_SCHEDULED_CONFLICT(40400008, "Film scheduled conflict"),
    EMPLOYEE_CODE_EXISTED(40400009, "Employee code existed"),
    USERNAME_EXISTED(40400010, "Username existed"),
    EMAIL_EXISTED(40400010, "Username existed"),
    PHONE_NUMBER_EXISTED(40400010, "Username existed"),
    PRICE_BY_TIME_REQUIRED(40400010, "Price by time required"),
    PRICE_BY_TIME_MUST_COVER_FULL_DAY(40400012, "Price by time must cover full day"),
    USER_HAS_NO_LOCATION(40400013, "User has no location"),
    OPEN_SHOWTIME_TEMPLATE_FAIL(40400013, "Open showtime template fail"),
    TEMPLATE_NOT_HAVE_FILM_SHEET(40400014, "Film sheet required"),
    TEMPLATE_NOT_HAVE_ROOM_SHEET(40400015, "Room sheet required"),
    DOWNLOAD_SHOWTIME_TEMPLATE_FAIL(40400016, "download showtime template fail"),
    READ_FILE_FAIL(40400016, "Read file fail"),
    INPUT_INVALID(40400017, "Input invalid"),
    ROOM_WITH_IDS_NOT_FOUND(40400018, "Room with ids {0} not found"),
    FILM_WITH_IDS_NOT_FOUND(40400019, "Film with ids {0} not found"),
    SHOWTIME_CONFLICT_WITH_ROOM_IDS(40400020, "Create showtime conflict with room ids {0}")
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
