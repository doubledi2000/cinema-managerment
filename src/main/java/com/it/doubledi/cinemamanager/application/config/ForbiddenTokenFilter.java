//package com.it.doubledi.cinemamanager.application.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.it.doubledi.cinemamanager._common.model.exception.AuthenticationError;
//import com.it.doubledi.cinemamanager._common.web.ErrorResponse;
//import com.it.doubledi.cinemamanager.application.service.impl.CustomUserDetailService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Slf4j
//@AllArgsConstructor
//public class ForbiddenTokenFilter extends OncePerRequestFilter {
//
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//    private static final String BEARER = "Bearer ";
//    private final TokenProvider tokenProvider;
//    private final CustomUserDetailService userDetailsService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "InvalidJWT");
//
//        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
//                ErrorResponse.builder()
//                        .error(AuthenticationError.UNAUTHORISED.getMessage())
//                        .code(AuthenticationError.UNAUTHORISED.getCode())
//                        .message("Invalid").build()));
//        return;
//    }
//
////    @Override
////    protected boolean shouldNotFilter(HttpServletRequest request) {
////        SecurityContext securityContext = SecurityContextHolder.getContext();
////        Authentication authentication = securityContext.getAuthentication();
////        if (authentication == null) {
////            return true;
////        }
////        return authentication instanceof AnonymousAuthenticationToken;
////    }
//
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken =request.getHeader(AUTHORIZATION_HEADER);
//        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(BEARER)) {
//            return  bearerToken.substring(BEARER.length());
//        }
//        return null;
//    }
//
//}
