package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;

public interface AccountService {
    AuthToken login(LoginRequest request);
}
