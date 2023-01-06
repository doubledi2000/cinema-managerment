package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdatePasswordRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UpdateProfileRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import com.it.doubledi.cinemamanager.domain.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface AccountResource {

    @PostMapping("/authenticate")
    Response<AuthToken> login(@RequestBody LoginRequest request);

    @GetMapping("/me/authorities")
    Response<UserAuthority> myAuthorities();

    @PostMapping("/me/update")
    Response<Boolean> updateProfile(@RequestBody UpdateProfileRequest request);

    @PostMapping("/me/change-password")
    Response<Boolean> changePassword(@RequestBody UpdatePasswordRequest request);

    @GetMapping("/me/my-profile")
    Response<User> myProfile();
}
