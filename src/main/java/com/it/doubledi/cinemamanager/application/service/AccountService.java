package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;

public interface AccountService {
    AuthToken login(LoginRequest request);

    UserAuthority myAuthorities();
}
