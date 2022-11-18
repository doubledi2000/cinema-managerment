package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface AccountResource {

    @PostMapping("/authenticate")
    Response<AuthToken> login(@RequestBody LoginRequest request);
}
