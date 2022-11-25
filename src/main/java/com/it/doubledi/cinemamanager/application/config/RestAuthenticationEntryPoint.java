//package com.it.doubledi.cinemamanager.application.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.it.doubledi.cinemamanager._common.model.exception.AuthenticationError;
//import com.it.doubledi.cinemamanager._common.web.ErrorResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@AllArgsConstructor
//public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
//        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "InvalidJWT");
//        ObjectMapper objectMapper = new ObjectMapper();
//        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
//                ErrorResponse.builder()
//                        .error(AuthenticationError.UNAUTHORISED.getMessage())
//                        .code(AuthenticationError.UNAUTHORISED.getCode())
//                        .message("Invalid").build()));
//    }
//}