package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthority;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.model.exception.AuthorizationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.security.AuthorityService;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.domain.repository.UserRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.*;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserAuthorityServiceImpl implements AuthorityService {

    private final UserRoleEntityRepository userRoleEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final RolePermissionEntityRepository rolePermissionEntityRepository;
    private final UserRepository userRepository;
    private final PermissionEntityRepository permissionEntityRepository;
    private final LocationEntityRepository locationEntityRepository;
    private final UserLocationEntityRepository userLocationEntityRepository;

    @Override
    public UserAuthority getUserAuthority(String userId) {
        User user = this.userRepository.getById(userId);
        List<String> grantedAuthorities = new ArrayList<>();
        boolean isRoot = false;
        List<UserRoleEntity> userRoleEntities = this.userRoleEntityRepository.findALlByUserIds(List.of(user.getId()));
        if (!CollectionUtils.isEmpty(userRoleEntities)) {
            List<String> roleIds = userRoleEntities.stream().map(UserRoleEntity::getRoleId).distinct().collect(Collectors.toList());

            List<RoleEntity> roleEntities = this.roleEntityRepository.findAllByIds(roleIds);
            roleIds = roleEntities.stream()
                    .filter(r -> Objects.equals(RoleStatus.ACTIVE, r.getStatus()))
                    .map(RoleEntity::getId)
                    .collect(Collectors.toList());
            isRoot = roleEntities.stream().anyMatch(r -> Boolean.TRUE.equals(r.getIsRoot()));
            List<RolePermissionEntity> rolePermissionEntities = this.rolePermissionEntityRepository.findAllByRoleIds(roleIds);
            List<PermissionEntity> permissionEntities = this.permissionEntityRepository.findALlByIds(
                    rolePermissionEntities.stream()
                            .map(RolePermissionEntity::getPermissionId)
                            .distinct()
                            .collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(permissionEntities)) {
                grantedAuthorities = permissionEntities.stream()
                        .map(r -> String.format("%s:%s", r.getResourceCode().toLowerCase(), r.getScope().toString().toLowerCase()))
                        .distinct()
                        .collect(Collectors.toList());
            }
        } else {
            log.info("User {} don't has role", userId);
        }
        return UserAuthority.builder()
                .isRoot(isRoot)
                .grantedPermissions(grantedAuthorities)
                .userId(user.getId())
                .userLevel(user.getUserLevel())
                .locationIds(getLocationIds(user))
                .build();
    }

    private List<String> getLocationIds(User user) {
        List<UserLocationEntity> userLocationEntities;
        Boolean isRoot = checkRoleAdmin(user.getId());
        List<String> locationIds;
        if (Boolean.TRUE.equals(isRoot) || Objects.equals(user.getUserLevel(), UserLevel.CENTER)) {
            List<LocationEntity> locationEntities = this.locationEntityRepository.findAllLocation();
            locationIds = locationEntities.stream().map(LocationEntity::getId).collect(Collectors.toList());
        } else {
            userLocationEntities = this.userLocationEntityRepository.findAllByUserIds(List.of(user.getId()));
            locationIds = userLocationEntities.stream().map(UserLocationEntity::getBuildingId).distinct().collect(Collectors.toList());
        }
        return locationIds;
    }

    private Boolean checkRoleAdmin(String userId) {
        List<UserRoleEntity> userRoleEntities = this.userRoleEntityRepository.findALlByUserIds(List.of(userId));

        if (CollectionUtils.isEmpty(userRoleEntities)) {
            throw new ResponseException(AuthorizationError.USER_DO_NOT_HAVE_ROLE);
        } else {
            List<String> roleIds = userRoleEntities.stream().map(UserRoleEntity::getRoleId).distinct().collect(Collectors.toList());
            List<RoleEntity> roleEntities = this.roleEntityRepository.findAllByIds(roleIds);
            Optional<RoleEntity> root = roleEntities.stream().filter(RoleEntity::getIsRoot).findFirst();
            return root.isPresent();
        }
    }
}
