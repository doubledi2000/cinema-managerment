package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LoginRequest;
import com.it.doubledi.cinemamanager.application.dto.response.AuthToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public interface AccountResource {

    @PostMapping("/authenticate")
    Response<AuthToken> login(@RequestBody LoginRequest request);

    @GetMapping("/me/authorities")
    Response<UserAuthority> myAuthorities();
}
