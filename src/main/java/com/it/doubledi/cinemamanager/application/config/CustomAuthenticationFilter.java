package com.it.doubledi.cinemamanager.application.config;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private final AuthorityService authorityService;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.resolveToken(request);
        String userId = this.tokenProvider.extractUserId(token);
        UserAuthority userAuthority = authorityService.getUserAuthority(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = new User(authentication.getName(), "", userAuthority.getGrantedPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        AbstractAuthenticationToken auth = new UserAuthentication(
                principal,
                authentication.getCredentials(),
                userAuthority.getGrantedPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()),
                userAuthority.getIsRoot(),
                userAuthority.getUserId(),
                userAuthority.getUserLevel(),
                token,
                userAuthority.getGrantedPermissions(),
                userAuthority.getLocationIds()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken =request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(BEARER)) {
            return  bearerToken.substring(BEARER.length());
        }
        return null;
    }
}
