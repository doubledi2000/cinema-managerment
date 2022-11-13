package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Role;
import com.it.doubledi.cinemamanager.domain.repository.RoleRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PermissionEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RolePermissionEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoleEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PermissionEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoleEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RolePermissionEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleDomainRepositoryImpl extends AbstractDomainRepository<Role, RoleEntity, String> implements RoleRepository {
    private final RolePermissionEntityRepository rolePermissionEntityRepository;
    private final PermissionEntityRepository permissionEntityRepository;

    public RoleDomainRepositoryImpl(RoleEntityRepository roleEntityRepository,
                                    RoleEntityMapper roleEntityMapper,
                                    RolePermissionEntityRepository rolePermissionEntityRepository,
                                    PermissionEntityRepository permissionEntityRepository) {
        super(roleEntityRepository, roleEntityMapper);
        this.rolePermissionEntityRepository = rolePermissionEntityRepository;
        this.permissionEntityRepository = permissionEntityRepository;
    }

    @Override
    public Role getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.ROLE_NOT_FOUND));
    }

    @Override
    protected List<Role> enrichList(List<Role> roles) {
        for (Role role : roles) {
            List<String> permissionIds;
            if (role.getIsRoot()) {
                List<PermissionEntity> permissionEntities = this.permissionEntityRepository.findAll();
                permissionIds = permissionEntities.stream().map(PermissionEntity::getId).collect(Collectors.toList());
            } else {
                List<RolePermissionEntity> rolePermissionEntities = this.rolePermissionEntityRepository.findAllByRoleIds(List.of(role.getId()));
                permissionIds = rolePermissionEntities.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());
            }
            role.enrichPermissionIds(permissionIds);
        }
        return roles;
    }
}
