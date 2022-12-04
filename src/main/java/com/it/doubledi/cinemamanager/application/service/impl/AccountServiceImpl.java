package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.model.exception.AuthenticationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.config.TokenProvider;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import com.it.doubledi.cinemamanager.application.service.AccountService;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserEntityRepository userEntityRepository;
    private final AuthorityService authorityService;
    @Override
    public AuthToken login(LoginRequest request) {
         Optional<UserEntity> userEntity = this.userEntityRepository.findUserByUsername(request.getUsername());
        if(userEntity.isEmpty()) {
            throw new ResponseException(NotFoundError.USER_NOT_FOUND);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername().toLowerCase(),
                request.getPassword(), new ArrayList<>());

        authentication = authenticationManager.authenticate(authentication);
        String accessToken = this.tokenProvider.createToken(authentication, userEntity.get().getId());

        return AuthToken.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public UserAuthority myAuthorities() {
        String me = currentUserId();
        return this.authorityService.getUserAuthority(me);
    }

    public String currentUserId() {
        Optional<String> userId = SecurityUtils.getCurrentUserLoginId();
        if (userId.isEmpty()) {
            throw new ResponseException(AuthenticationError.UNAUTHORISED);
        }
        return userId.get();
    }
}
