package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.UserService;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.presentation.web.UserResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    @Override
    public Response<User> create(UserCreateRequest request) {
        return Response.of(userService.create(request));
    }

    @Override
    public Response<User> update(String id, UserUpdateRequest request) {
        return Response.of(update(id, request));
    }

    @Override
    public Response<User> getById(String id) {
        return Response.of(userService.findById(id));
    }

    @Override
    public PagingResponse<User> search(UserSearchRequest request) {
        return PagingResponse.of(userService.search(request));
    }
}
