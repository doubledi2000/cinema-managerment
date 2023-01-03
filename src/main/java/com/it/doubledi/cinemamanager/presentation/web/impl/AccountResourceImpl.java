package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdatePasswordRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdateProfileRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import com.it.doubledi.cinemamanager.application.service.AccountService;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.presentation.web.AccountResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AccountResourceImpl implements AccountResource {

    private final AccountService accountService;

    @Override
    public Response<AuthToken> login(LoginRequest request) {

        return Response.of(accountService.login(request));
    }

    @Override
    public Response<UserAuthority> myAuthorities() {
        return Response.of(accountService.myAuthorities());
    }

    @Override
    public Response<Boolean> updateProfile(UpdateProfileRequest request) {
        this.accountService.updateProfile(request);
        return Response.ok();
    }

    @Override
    public Response<Boolean> changePassword(UpdatePasswordRequest request) {
        this.accountService.changePassword(request);
        return Response.ok();
    }

    @Override
    public Response<User> myProfile() {
        return Response.of(this.accountService.myProfile());
    }
}
