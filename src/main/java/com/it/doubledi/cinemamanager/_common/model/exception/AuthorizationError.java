package com.it.doubledi.cinemamanager._common.model.exception;

import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import lombok.Getter;

@Getter
public enum AuthorizationError implements ResponseError {
    USER_DO_NOT_HAVE_ROLE(40301001, "You don't have role"),
    NOT_SUPPORT_AUTHENTICATION(40300003, "Your authentication has not been supported yet"),
    ACCESS_DENIED(40300001, "Access Denied"),
    ;

    private final Integer code;
    private final String message;

    AuthorizationError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 403;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
