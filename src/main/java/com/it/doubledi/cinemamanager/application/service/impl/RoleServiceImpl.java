package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.*;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.RoleService;
import com.it.doubledi.cinemamanager.domain.Permission;
import com.it.doubledi.cinemamanager.domain.Role;
import com.it.doubledi.cinemamanager.domain.command.RoleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RolePermittedCmd;
import com.it.doubledi.cinemamanager.domain.command.RoleUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.RoleSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.RoleRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PermissionEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoleEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PermissionEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoleEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AutoMapper autoMapper;
    private final AutoMapperQuery autoMapperQuery;
    private final RoleEntityMapper roleEntityMapper;
    private final RoleEntityRepository roleEntityRepository;
    private final PermissionEntityRepository permissionEntityRepository;
    private final PermissionEntityMapper permissionEntityMapper;

    @Override
    public Role create(RoleCreateRequest request) {
        RoleCreateCmd cmd = this.autoMapper.from(request);
        Role role = new Role(cmd);
        this.roleRepository.save(role);
        return role;
    }

    @Override
    public Role update(String id, RoleUpdateRequest request) {
        RoleUpdateCmd cmd = this.autoMapper.from(request);
        Role role = this.roleRepository.getById(id);
        role.update(cmd);
        this.roleRepository.save(role);
        return role;
    }

    @Override
    public Role findById(String id) {
        return this.roleRepository.getById(id);
    }

    @Override
    public PageDTO<Role> search(RoleSearchRequest request) {
        RoleSearchQuery query = this.autoMapperQuery.toQuery(request);
        Long count = this.roleEntityRepository.count(query);
        if (count == 0) {
            return PageDTO.empty();
        }

        List<RoleEntity> roleEntities = this.roleEntityRepository.search(query);
        List<Role> roles = this.roleEntityMapper.toDomain(roleEntities);
        return new PageDTO(roles, query.getPageIndex(), request.getPageSize(), count);
    }

    @Override
    public PageDTO<Role> autoComplete(RoleSearchRequest request) {
        return null;
    }

    @Override
    public List<Permission> findAllPermission() {
        return this.permissionEntityMapper.toDomain(this.permissionEntityRepository.findAll());
    }

    @Override
    public Role permission(String id, RolePermittedRequest request) {
        Role role = this.roleRepository.getById(id);
        RolePermittedCmd cmd = this.autoMapper.from(request);
        role.updatePermission(cmd);
        this.roleRepository.save(role);
        return role;
    }

    @Override
    public Role permission(String id, RolePermissionRequest request) {
        Role role = this.roleRepository.getById(id);
        return null;
    }
}
