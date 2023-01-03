package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.*;
import com.it.doubledi.cinemamanager.domain.Permission;
import com.it.doubledi.cinemamanager.domain.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface RoleResource {

    @PostMapping("/roles")
    @PreAuthorize("hasPermission(null, 'role:create')")
    Response<Role> create(@RequestBody RoleCreateRequest request);

    @PostMapping("/roles/{id}/update")
    @PreAuthorize("hasPermission(null, 'role:update')")
    Response<Role> update(@PathVariable("id") String id, @RequestBody RoleUpdateRequest request);

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasPermission(null, 'role:view')")
    Response<Role> findById(@PathVariable("id") String id);

    @GetMapping("/roles")
    @PreAuthorize("hasPermission(null, 'role:view')")
    PagingResponse<Role> search(RoleSearchRequest request);

    @GetMapping("/permissions/find-all")
    @PreAuthorize("hasPermission(null, 'role:view')")
    Response<List<Permission>> findAllPermission();

    @PostMapping("/roles/{id}/permit")
    @PreAuthorize("hasPermission(null, 'role:update')")
    Response<Role> permit(@PathVariable("id") String id, @RequestBody RolePermissionRequest request);

    @PostMapping("/roles/find-by-ids")
    Response<List<Role>> findByIds(@RequestBody FindByIdsRequest request);
}
