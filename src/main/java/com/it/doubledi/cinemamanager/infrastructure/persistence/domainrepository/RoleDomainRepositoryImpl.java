package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Role;
import com.it.doubledi.cinemamanager.domain.RolePermission;
import com.it.doubledi.cinemamanager.domain.repository.RoleRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PermissionEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RolePermissionEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoleEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RolePermissionEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PermissionEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoleEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RolePermissionEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class RoleDomainRepositoryImpl extends AbstractDomainRepository<Role, RoleEntity, String> implements RoleRepository {
    private final RolePermissionEntityRepository rolePermissionEntityRepository;
    private final PermissionEntityRepository permissionEntityRepository;
    private final RolePermissionEntityMapper rolePermissionEntityMapper;

    public RoleDomainRepositoryImpl(RoleEntityRepository roleEntityRepository,
                                    RoleEntityMapper roleEntityMapper,
                                    RolePermissionEntityRepository rolePermissionEntityRepository,
                                    PermissionEntityRepository permissionEntityRepository,
                                    RolePermissionEntityMapper rolePermissionEntityMapper) {
        super(roleEntityRepository, roleEntityMapper);
        this.rolePermissionEntityRepository = rolePermissionEntityRepository;
        this.permissionEntityRepository = permissionEntityRepository;
        this.rolePermissionEntityMapper = rolePermissionEntityMapper;
    }

    @Override
    public Role getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.ROLE_NOT_FOUND));
    }

    @Override
    @Transactional
    public List<Role> saveALl(List<Role> domains) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        domains.forEach(r -> {
            if(!CollectionUtils.isEmpty(r.getPermissions())) {
                rolePermissions.addAll(r.getPermissions());
            }
        });
        List<RolePermissionEntity> rolePermissionEntities = this.rolePermissionEntityMapper.toEntity(rolePermissions);
        this.rolePermissionEntityRepository.saveAll(rolePermissionEntities);
        return super.saveALl(domains);
    }

    @Override
    protected List<Role> enrichList(List<Role> roles) {
        List<RolePermissionEntity> rolePermissionEntities = this.rolePermissionEntityRepository.findAllByRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));
        for (Role role : roles) {
            List<String> permissionIds;
            List<RolePermission> rolePermissions;
            if (role.getIsRoot()) {
                List<PermissionEntity> permissionEntities = this.permissionEntityRepository.findAll();
                permissionIds = permissionEntities.stream().map(PermissionEntity::getId).collect(Collectors.toList());
            } else {

                permissionIds = rolePermissionEntities.stream()
                        .filter(r -> Objects.equals(r.getRoleId(), role.getId()))
                        .map(RolePermissionEntity::getPermissionId)
                        .collect(Collectors.toList());

                rolePermissions = this.rolePermissionEntityMapper.toDomain(
                        rolePermissionEntities.stream()
                                .filter(r -> Objects.equals(r.getRoleId(), role.getId()))
                                .collect(Collectors.toList()));
                role.enrichRolePermission(rolePermissions);
            }

            role.enrichPermissionIds(permissionIds);
        }
        return roles;
    }
}
