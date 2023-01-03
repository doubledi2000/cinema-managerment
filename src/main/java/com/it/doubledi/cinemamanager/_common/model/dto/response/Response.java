package com.it.doubledi.cinemamanager._common.model.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import com.it.doubledi.cinemamanager.domain.Location;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    protected T data;
    private Boolean success;
    private int code;
    private String message;
    private long timestamp;

    private RuntimeException exception;

    public Response() {
        timestamp = Instant.now().toEpochMilli();
        success = true;
        code = 200;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.success();
        return response;
    }

    public static <T> Response of(T res) {
        Response response = new Response();
        response.data = res;
        response.success();
        return response;
    }

    public static <T> Response<T> fail(RuntimeException runtimeException) {
        Response<T> response = new Response<>();
        response.setSuccess(Boolean.FALSE);
        response.setException(runtimeException);
        return response;
    }

    public Response<T> success() {
        success = true;
        code = 200;
        return this;
    }

    public Response<T> data(T res) {
        this.data = res;
        return this;
    }

    public Response<T> fail(String message, ResponseError responseError) {
        success = false;
        code = responseError.getCode();
        if (StringUtils.hasText(message)) {
            this.message = message;
        } else {
            this.message = responseError.getMessage();
        }
        return this;
    }

    public Response<T> fail(Exception exception, ResponseError responseError) {
        success = false;
        code = responseError.getCode();
        this.message = exception.getMessage();
        return this;
    }

    public T getData() {
        if (exception != null) {
            throw exception;
        }
        return data;
    }

    public boolean getSuccess() {
        if (exception != null) {
            throw exception;
        }
        return success;
    }

    @Override
    public String toString() {
        return "Response {" +
                "data=" + data +
                ", success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", exception=" + exception +
                '}';
    }


}
