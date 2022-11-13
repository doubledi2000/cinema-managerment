package com.it.doubledi.cinemamanager.application;

import com.it.doubledi.cinemamanager._common.model.enums.Scope;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PermissionEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserRoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PermissionEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoleEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserRoleEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ResourceCategory;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleLevel;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.UserStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SeedDatabaseRunner implements CommandLineRunner {

    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PermissionEntityRepository permissionEntityRepository;
    private final UserRoleEntityRepository userRoleEntityRepository;
    private final String ADMIN = "admin";

    public SeedDatabaseRunner(UserEntityRepository userEntityRepository,
                              RoleEntityRepository roleEntityRepository,
                              PermissionEntityRepository permissionEntityRepository,
                              UserRoleEntityRepository userRoleEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.permissionEntityRepository = permissionEntityRepository;
        this.userRoleEntityRepository = userRoleEntityRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Optional<UserEntity> userEntity = this.userEntityRepository.findUserByUsername(ADMIN);
        String roleId;
        String userId;
        if (userEntity.isEmpty()) {
            UserEntity u = UserEntity.builder()
                    .id(IdUtils.nextId())
                    .username(ADMIN)
                    .password(ADMIN)
                    .fullName(ADMIN)
                    .deleted(Boolean.FALSE)
                    .status(UserStatus.ACTIVE)
                    .build();
            this.userEntityRepository.save(u);
            userId = u.getId();
        } else {
            userId = userEntity.get().getId();
        }
        Optional<RoleEntity> roleEntityOptional = this.roleEntityRepository.findRoleByCode(ADMIN);
        if (roleEntityOptional.isEmpty()) {
            RoleEntity r = RoleEntity.builder()
                    .id(IdUtils.nextId())
                    .code(ADMIN)
                    .name(ADMIN)
                    .status(RoleStatus.ACTIVE)
                    .deleted(Boolean.FALSE)
                    .isRoot(Boolean.TRUE)
                    .roleLevel(RoleLevel.CENTER)
                    .build();
            roleEntityRepository.save(r);
            roleId = r.getId();
        } else {
            roleId = roleEntityOptional.get().getId();
        }

        Optional<UserRoleEntity> userRoleEntityOptional = this.userRoleEntityRepository.findByUserAndRole(userId, roleId);
        if (userRoleEntityOptional.isEmpty()) {
            UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                    .id(IdUtils.nextId())
                    .userId(userId)
                    .roleId(roleId)
                    .deleted(Boolean.FALSE)
                    .build();
            userRoleEntityRepository.save(userRoleEntity);
        }

        List<PermissionEntity> permissionEntities = new ArrayList<>();
        ResourceCategory[] resourceCategories = ResourceCategory.values();
        List<PermissionEntity> permissionEntitiesExisted = this.permissionEntityRepository.findAll();
        for (ResourceCategory resourceCategory : resourceCategories) {
            if (CollectionUtils.isEmpty(resourceCategory.getScopes())) {
                continue;
            }

            for (Scope scope : resourceCategory.getScopes()) {
                String scopeName = "Xem";
                switch (scope) {
                    case VIEW:
                        scopeName = "XEM";
                        break;
                    case CREATE:
                        scopeName = "Tạo";
                        break;
                    case UPDATE:
                        scopeName = "Cập nhật";
                        break;
                    case DELETE:
                        scopeName = "Xóa";
                        break;
                }
                Optional<PermissionEntity> permissionOptional = permissionEntitiesExisted.stream()
                        .filter(f -> Objects.equals(f.getResourceCode(), resourceCategory.getResourceCode()) && Objects.equals(scope, f.getScope()))
                        .findFirst();
                if(permissionOptional.isPresent()) {
                    continue;
                }
                PermissionEntity permissionEntity = PermissionEntity.builder()
                        .id(IdUtils.nextId())
                        .name(scopeName)
                        .resourceCode(resourceCategory.getResourceCode())
                        .scope(scope)
                        .deleted(Boolean.FALSE)
                        .priority(resourceCategory.getPriority())
                        .build();
                permissionEntities.add(permissionEntity);
            }
        }
        permissionEntityRepository.saveAll(permissionEntities);
    }
}
