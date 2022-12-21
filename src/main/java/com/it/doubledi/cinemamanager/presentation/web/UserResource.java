package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.RoleUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface UserResource {

    @PostMapping("/users")
    @PreAuthorize("hasPermission(null, 'user:create')")
    Response<User> create(@RequestBody UserCreateRequest request);

    @PostMapping("/users/{id}/update")
    @PreAuthorize("hasPermission(null, 'user:update')")
    Response<User> update(@PathVariable("id") String id, @RequestBody UserUpdateRequest request);

    @GetMapping("/users/{id}")
    @PreAuthorize("hasPermission(null, 'user:view')")
    Response<User> getById(@PathVariable("id") String id);

    @GetMapping("/users")
    @PreAuthorize("hasPermission(null, 'user:view')")
    PagingResponse<User> search(UserSearchRequest request);

}
