package com.it.doubledi.cinemamanager.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.doubledi.cinemamanager._common.model.dto.error.ResponseError;
import com.it.doubledi.cinemamanager._common.model.exception.AuthenticationError;
import com.it.doubledi.cinemamanager._common.model.exception.AuthorizationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.ErrorResponse;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("handler access denied");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        ObjectMapper objectMapper = new ObjectMapper();
        httpServletResponse.getWriter().write(
                ErrorResponse.builder()
                        .error(AuthenticationError.UNAUTHORISED.getMessage())
                        .code(AuthenticationError.UNAUTHORISED.getCode())
                        .message("Invalid").build().toString());
        httpServletResponse.getWriter().flush();
    }
}