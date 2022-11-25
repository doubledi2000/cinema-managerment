package com.it.doubledi.cinemamanager._common.web.config;

import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import com.it.doubledi.cinemamanager._common.model.exception.AuthorizationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.ErrorResponse;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

//@Configuration
//@ControllerAdvice
//@Order
@Slf4j
public class ExceptionHandlerAdvice {
    private final String EXCEPTION_MESSAGE = "custom_exception_message";

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorResponse<Object>> handleValidationException(HttpClientErrorException.Forbidden e, HttpServletRequest request){
        log.warn("Failed to handle request " + request.getMethod() + ": " + request.getRequestURI() + ": " + e.getMessage(), e);
        catchException(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .code(AuthorizationError.ACCESS_DENIED.getCode())
                        .error(AuthorizationError.ACCESS_DENIED.getName())
                        .message(AuthorizationError.ACCESS_DENIED.getMessage())
                        .build());
    }

//
//    @ExceptionHandler({ResponseException.class})
//    public ResponseEntity<ErrorResponse<Object>> handleResponseException(ResponseException e, HttpServletRequest request) {
//        log.warn("Failed to handle request {}: {}", request.getRequestURI(), e.getError().getMessage(), e);
//        ResponseError error = e.getError();
//        catchException(e);
//        return ResponseEntity.status(error.getStatus())
//                .body(ErrorResponse.builder()
//                        .code(error.getCode())
//                        .error(error.getName())
//                        .message(error.getMessage())
//                        .build());
//
//    }

    private void catchException(Exception exception) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            requestAttributes.setAttribute(EXCEPTION_MESSAGE, exception, RequestAttributes.SCOPE_REQUEST);
        }
    }
}
