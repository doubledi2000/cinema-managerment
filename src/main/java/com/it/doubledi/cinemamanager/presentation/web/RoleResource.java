package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.RoleCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoleSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoleUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Permission;
import com.it.doubledi.cinemamanager.domain.Role;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
public interface RoleResource {

    @PostMapping("/roles")
    Response<Role> create(@RequestBody RoleCreateRequest request);

    @PostMapping("/roles/{id}/update")
    Response<Role> update(@PathVariable("id") String id, @RequestBody RoleUpdateRequest request);

    @GetMapping("/roles/{id}")
    Response<Role> findById(@PathVariable("id") String id);

    @GetMapping("/roles")
    PagingResponse<Role> search(RoleSearchRequest request);

    @GetMapping("/permissions/find-all")
    Response<List<Permission>> findAllPermission();
}
