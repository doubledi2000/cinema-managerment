package com.it.doubledi.cinemamanager.application.config;

import com.it.doubledi.cinemamanager.application.service.impl.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ForbiddenTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private final TokenProvider tokenProvider;
    private final CustomUserDetailService userDetailsService;

    public ForbiddenTokenFilter(TokenProvider tokenProvider,
                                CustomUserDetailService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.resolveToken(request);
//        String username =  tokenProvider.extractSubject(token);
//        if (tokenProvider.validateToken(token, userDetails))
        filterChain.doFilter(request, response);
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        if (authentication == null) {
//            return true;
//        }
//        return authentication instanceof AnonymousAuthenticationToken;
//    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken =request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(BEARER)) {
            return  bearerToken.substring(BEARER.length());
        }
        return null;
    }

}
