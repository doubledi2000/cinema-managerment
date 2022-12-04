//package com.it.doubledi.cinemamanager.application.config;
//
//import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
//import com.it.doubledi.cinemamanager._common.model.UserAuthority;
//import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
//import com.it.doubledi.cinemamanager.application.service.impl.CustomUserDetailService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
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
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//@AllArgsConstructor
//public class CustomAuthenticationFilter extends OncePerRequestFilter {
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//    private static final String BEARER = "Bearer ";
//    private final CustomUserDetailService authorityService;
//    private final TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//
//        String token = null;
//        String userName = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            token = authorizationHeader.substring(7);
//            userName = tokenProvider.extractSubject(token);
//        }
//
//        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = authorityService.loadUserByUsername(userName);
//
//            if (tokenProvider.validateToken(token, userDetails)) {
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
////    @Override
////    protected boolean shouldNotFilter(HttpServletRequest request) {
////        SecurityContext securityContext = SecurityContextHolder.getContext();
////        Authentication authentication = securityContext.getAuthentication();
////        if (authentication == null) {
////            return true;
////        }
////        if (authentication instanceof UsernamePasswordAuthenticationToken) {
////            return !authentication.isAuthenticated();
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
//}
