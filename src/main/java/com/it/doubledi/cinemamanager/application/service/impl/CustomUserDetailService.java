package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.exception.AuthenticationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;
    private final AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!StringUtils.hasLength(username)) {
            throw new ResponseException(AuthenticationError.UNAUTHORISED);
        }
        Optional<UserEntity> userEntityOptional = this.userEntityRepository.findUserByUsername(username);
        if(userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return enrichUserInfo(userEntity);
        }else {
            throw new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND, username);
        }
    }

    private User enrichUserInfo(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<String> listAuthorities = this.authorityService.getUserAuthority(userEntity.getId()).getGrantedPermissions();
        authorities = CollectionUtils.isEmpty(listAuthorities) ? authorities
                : listAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
