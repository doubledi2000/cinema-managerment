package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.config.TokenProvider;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdatePasswordRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdateProfileRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.AccountService;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.domain.command.UpdateProfileCmd;
import com.it.doubledi.cinemamanager.domain.repository.UserRepository;
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
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserEntityRepository userEntityRepository;
    private final AuthorityService authorityService;
    private final UserRepository userRepository;
    private final AutoMapper autoMapper;

    @Override
    public AuthToken login(LoginRequest request) {
        Optional<UserEntity> userEntity = this.userEntityRepository.findUserByUsername(request.getUsername());
        if (userEntity.isEmpty()) {
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
        String me = SecurityUtils.getCurrentUserLoginId();
        return this.authorityService.getUserAuthority(me);
    }

    @Override
    public void updateProfile(UpdateProfileRequest request) {
        String me = SecurityUtils.getCurrentUserLoginId();
        User user = this.userRepository.getById(me);
        UpdateProfileCmd cmd = this.autoMapper.from(request);
        if (!Objects.equals(cmd.getEmail(), user.getEmail())) {
            this.userEntityRepository.findByEmail(cmd.getEmail()).orElseThrow(() -> new ResponseException(BadRequestError.EMAIL_EXISTED));
        }

        if (!Objects.equals(cmd.getPhoneNumber(), user.getPhoneNumber())) {
            this.userEntityRepository.findByPhoneNumber(cmd.getPhoneNumber()).orElseThrow(() -> new ResponseException(BadRequestError.PHONE_NUMBER_EXISTED));
        }
        user.update(cmd);
        this.userRepository.save(user);
    }

    @Override
    public void changePassword(UpdatePasswordRequest request) {
        String me = SecurityUtils.getCurrentUserLoginId();
        User user = this.userRepository.getById(me);
        user.changePassword(request);
        this.userRepository.save(user);
    }

    @Override
    public User myProfile() {
        String me = SecurityUtils.getCurrentUserLoginId();
        return this.userRepository.getById(me);
    }
}
