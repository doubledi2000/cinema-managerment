package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.RoleUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.domain.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface UserResource {

    @PostMapping("/users")
    Response<User> create(@RequestBody UserCreateRequest request);

    @PostMapping("/users/{id}/update")
    Response<User> update(@PathVariable("id") String id, @RequestBody UserUpdateRequest request);

    @GetMapping("/users/{id}")
    Response<User> getById(@PathVariable("id") String id);

    @GetMapping("/users")
    PagingResponse<User> search(UserSearchRequest request);

}
