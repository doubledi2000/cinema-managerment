package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.RoleCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RolePermissionRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RolePermittedRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoleSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoleUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.RoleService;
import com.it.doubledi.cinemamanager.domain.Permission;
import com.it.doubledi.cinemamanager.domain.Role;
import com.it.doubledi.cinemamanager.presentation.web.RoleResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleResourceImpl implements RoleResource {
    private final RoleService roleService;

    @Override
    public Response<Role> create(RoleCreateRequest request) {
        return Response.of(roleService.create(request));
    }

    @Override
    public Response<Role> update(String id, RoleUpdateRequest request) {
        return Response.of(roleService.update(id, request));
    }

    @Override
    public Response<Role> findById(String id) {
        return Response.of(roleService.findById(id));
    }

    @Override
    public PagingResponse<Role> search(RoleSearchRequest request) {
        return PagingResponse.of(roleService.search(request));
    }

    @Override
    public Response<List<Permission>> findAllPermission() {
        return Response.of(this.roleService.findAllPermission());
    }

    @Override
    public Response<Role> permit(String id, RolePermissionRequest request) {
        return Response.of(this.roleService.permission(id, request));
    }
}
