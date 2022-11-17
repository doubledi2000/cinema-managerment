package com.it.doubledi.cinemamanager._common.model;

import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private final boolean isRoot;
    private final String userId;
    private final UserLevel userLevel;
    private final String token;
    private final List<String> grantedPermissions;
    private final List<String> locationIds;

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, boolean isRoot, String userId, UserLevel userLevel, String token, List<String> grantedPermissions, List<String> locationIds) {
        super(principal, credentials, authorities);
        this.isRoot = isRoot;
        this.userId = userId;
        this.userLevel = userLevel;
        this.token = token;
        this.grantedPermissions = CollectionUtils.isEmpty(authorities) ? new ArrayList<>()
                : authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());;
        this.locationIds = locationIds;
    }
}
