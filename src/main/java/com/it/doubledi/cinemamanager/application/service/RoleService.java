package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.RoleCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoleSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoleUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Permission;
import com.it.doubledi.cinemamanager.domain.Role;

import java.util.List;

public interface RoleService {
    Role create(RoleCreateRequest request);

    Role update(String id, RoleUpdateRequest request);

    Role findById(String id);

    PageDTO<Role> search(RoleSearchRequest request);

    PageDTO<Role> autoComplete(RoleSearchRequest request);

    List<Permission> findAllPermission();
}
