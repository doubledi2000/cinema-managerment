package com.it.doubledi.cinemamanager.application.config;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
import com.it.doubledi.cinemamanager.application.service.impl.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailService service;
    private final AuthorityService authorityService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = tokenProvider.extractSubject(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(userName);

            if (tokenProvider.validateToken(token, userDetails)) {
                String userId = tokenProvider.extractUserId(token);
                UserAuthority userAuthority = this.authorityService.getUserAuthority(userId);
                Set<SimpleGrantedAuthority> grantedAuthorities = userAuthority.getGrantedPermissions().stream()
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
                User user = new User(userName, "", grantedAuthorities);
                UserAuthentication auth = new UserAuthentication(user, token, grantedAuthorities, userAuthority.getIsRoot(), userId, userAuthority.getUserLevel(), token, grantedAuthorities, userAuthority.getLocationIds());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}