package com.it.doubledi.cinemamanager._common.web.config;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import com.it.doubledi.cinemamanager._common.model.exception.AuthorizationError;
import com.it.doubledi.cinemamanager._common.model.exception.FieldErrorResponse;
import com.it.doubledi.cinemamanager._common.model.exception.InvalidInputResponse;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.ErrorResponse;
import com.it.doubledi.cinemamanager._common.web.i18n.LocaleStringService;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ControllerAdvice
@Order
@Slf4j
public class ExceptionHandlerAdvice {
    private final String EXCEPTION_MESSAGE = "custom_exception_message";

    @Autowired
    private LocaleStringService localeStringService;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<Object>> handleValidationException(AccessDeniedException e, HttpServletRequest request) {
        log.warn("Failed to handle request " + request.getMethod() + ": " + request.getRequestURI() + ": " + e.getMessage(), e);
        catchException(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .code(AuthorizationError.ACCESS_DENIED.getCode())
                        .error(AuthorizationError.ACCESS_DENIED.getName())
                        .message(AuthorizationError.ACCESS_DENIED.getMessage())
                        .build());
    }


    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse<Object>> handleResponseException(ResponseException e, HttpServletRequest request) {
        log.warn("Failed to handle request {}: {}", request.getRequestURI(), e.getError().getMessage(), e);
        ResponseError error = e.getError();
        String message = this.localeStringService.getMessages(error.getName(), error.getMessage(), e.getParams());
        catchException(e);
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.builder()
                        .code(error.getCode())
                        .error(error.getName())
                        .message(message)
                        .build());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidInputResponse> handleValidationException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        BindingResult bindingResult = e.getBindingResult();
        Set<FieldErrorResponse> fieldErrors = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    try {
                        FieldError fieldError = (FieldError) objectError;
                        String message = localeStringService.getMessages(
                                fieldError.getDefaultMessage(), fieldError.getDefaultMessage());
                        return FieldErrorResponse.builder()
                                .field(fieldError.getField())
                                .objectName(fieldError.getObjectName())
                                .message(message)
                                .build();
                    } catch (ClassCastException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        log.warn("Failed to handle request " + request.getRequestURI() + ": " + e.getMessage(), e);

        catchException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InvalidInputResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        localeStringService.getMessages(
                                BadRequestError.INVALID_INPUT.getName(),
                                "Invalid request arguments"),
                        BadRequestError.INVALID_INPUT.getName(),
                        fieldErrors));

    }

    private void catchException(Exception exception) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            requestAttributes.setAttribute(EXCEPTION_MESSAGE, exception, RequestAttributes.SCOPE_REQUEST);
        }
    }
}
