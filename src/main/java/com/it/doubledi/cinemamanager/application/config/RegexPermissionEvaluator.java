package com.it.doubledi.cinemamanager.application.config;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.exception.AuthorizationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.regex.Pattern;

@Component
@Slf4j
public class RegexPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String requiredPermission = permission.toString();
        log.warn("Regex PermissionEvaluator hasPermission");
//        if(!(authentication instanceof UserAuthentication)) {
//            throw new ResponseException(AuthorizationError.NOT_SUPPORT_AUTHENTICATION);
//        }
//        UserAuthentication userAuthentication = (UserAuthentication) authentication;
        return authentication.getAuthorities().stream().anyMatch(p->Pattern.matches(p.getAuthority(),requiredPermission));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, null, permission);
    }
}
