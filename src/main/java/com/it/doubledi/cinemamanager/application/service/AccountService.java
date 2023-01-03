package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdatePasswordRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdateProfileRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import com.it.doubledi.cinemamanager.domain.User;

public interface AccountService {
    AuthToken login(LoginRequest request);

    UserAuthority myAuthorities();

    void updateProfile(UpdateProfileRequest request);

    void changePassword(UpdatePasswordRequest request);

    User myProfile();
}
