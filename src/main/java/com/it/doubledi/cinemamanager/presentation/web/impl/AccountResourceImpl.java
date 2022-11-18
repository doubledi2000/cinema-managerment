package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import com.it.doubledi.cinemamanager.application.service.AccountService;
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
}
