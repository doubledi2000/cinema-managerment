package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.*;
import com.it.doubledi.cinemamanager.domain.repository.UserRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.*;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDomainRepositoryImpl extends AbstractDomainRepository<User, UserEntity, String> implements UserRepository {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityMapper userEntityMapper;
    private final UserLocationEntityRepository userLocationEntityRepository;
    private final UserLocationEntityMapper userLocationEntityMapper;
    private final LocationEntityRepository locationEntityRepository;
    private final LocationEntityMapper locationEntityMapper;
    private final UserRoleEntityMapper userRoleEntityMapper;
    private final UserRoleEntityRepository userRoleEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final FileEntityRepository fileEntityRepository;

    public UserDomainRepositoryImpl(UserEntityRepository userEntityRepository,
                                    UserEntityMapper userEntityMapper,
                                    UserLocationEntityRepository userLocationEntityRepository,
                                    UserLocationEntityMapper userLocationEntityMapper,
                                    LocationEntityRepository locationEntityRepository,
                                    LocationEntityMapper locationEntityMapper,
                                    UserRoleEntityMapper userRoleEntityMapper,
                                    UserRoleEntityRepository userRoleEntityRepository,
                                    RoleEntityRepository roleEntityRepository,
                                    RoleEntityMapper roleEntityMapper,
                                    FileEntityRepository fileEntityRepository) {
        super(userEntityRepository, userEntityMapper);
        this.userEntityRepository = userEntityRepository;
        this.userEntityMapper = userEntityMapper;
        this.userLocationEntityRepository = userLocationEntityRepository;
        this.userLocationEntityMapper = userLocationEntityMapper;
        this.locationEntityRepository = locationEntityRepository;
        this.locationEntityMapper = locationEntityMapper;
        this.userRoleEntityMapper = userRoleEntityMapper;
        this.userRoleEntityRepository = userRoleEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.roleEntityMapper = roleEntityMapper;
        this.fileEntityRepository = fileEntityRepository;
    }

    @Override
    public User getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.USER_NOT_FOUND));
    }

    @Override
    public User save(User domain) {
        this.saveALl(List.of(domain));
        return domain;
    }

    @Override
    @Transactional
    public List<User> saveALl(List<User> domains) {
        List<UserLocation> userLocations = new ArrayList<>();
        List<UserRole> userRoles = new ArrayList<>();

        domains.forEach(e -> {
            if (!CollectionUtils.isEmpty(e.getLocations())) {
                userLocations.addAll(e.getLocations());
            }
            if (!CollectionUtils.isEmpty(e.getRoles())) {
                userRoles.addAll(e.getRoles());
            }
        });
        this.userLocationEntityRepository.saveAll(this.userLocationEntityMapper.toEntity(userLocations));
        this.userRoleEntityRepository.saveAll(this.userRoleEntityMapper.toEntity(userRoles));

        return this.saveALl(domains);
    }

    @Override
    public List<User> enrichList(List<User> users) {
        List<String> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        List<UserLocationEntity> userLocationEntities = this.userLocationEntityRepository.findAllByUserIds(userIds);
        List<UserLocation> userLocations = this.userLocationEntityMapper.toDomain(userLocationEntities);

        List<String> locationIds = userLocations.stream().map(UserLocation::getBuildingId).distinct().collect(Collectors.toList());
        List<LocationEntity> locationEntities = this.locationEntityRepository.findByIds(locationIds);
        List<Location> locations = this.locationEntityMapper.toDomain(locationEntities);

        userLocations.forEach(e -> {
            Optional<Location> locationTmp = locations.stream().filter(l -> Objects.equals(l.getId(), e.getBuildingId())).findFirst();
            locationTmp.ifPresent(e::enrichLocation);
        });

        List<UserRoleEntity> userRoleEntities = this.userRoleEntityRepository.findALlByUserIds(userIds);
        List<UserRole> userRoles = this.userRoleEntityMapper.toDomain(userRoleEntities);
        List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleEntity> roleEntities = this.roleEntityRepository.findAllByIds(roleIds);
        List<Role> roles = this.roleEntityMapper.toDomain(roleEntities);
        userRoles.forEach(e -> {
            Optional<Role> roleTmp = roles.stream().filter(r -> Objects.equals(r.getId(), e.getRoleId())).findFirst();
            roleTmp.ifPresent(e::enrichRole);
        });

        List<String> fileIds = users.stream().map(User::getAvatarFileId).filter(StringUtils::hasLength).collect(Collectors.toList());
        List<FileEntity> fileEntities = this.fileEntityRepository.findByIds(fileIds);
        for (User user : users) {
            List<String> locationIdsTmp = userLocations.stream()
                    .filter(u -> Objects.equals(user.getId(), u.getUserId()))
                    .map(UserLocation::getBuildingId)
                    .collect(Collectors.toList());

            user.enrichLocationIds(locationIdsTmp);
            List<UserLocation> userLocationsTmp = userLocations.stream().filter(u -> Objects.equals(u.getUserId(), user.getId())).collect(Collectors.toList());
            user.enrichUserLocation(userLocationsTmp);

            List<String> roleIdsTmp = userRoles.stream().filter(u -> Objects.equals(u.getUserId(), user.getId())).map(UserRole::getRoleId).collect(Collectors.toList());
            user.enrichRoleIds(roleIdsTmp);
            List<UserRole> userRolesTmp = userRoles.stream().filter(u -> Objects.equals(u.getUserId(), user.getId())).collect(Collectors.toList());
            user.enrichUserRole(userRolesTmp);

            Optional<FileEntity> fileEntityOptional = fileEntities.stream().filter(f -> Objects.equals(f.getId(), user.getAvatarFileId())).findFirst();
            fileEntityOptional.ifPresent(f -> {
                user.enrichAvatar(f.getPath());
            });
        }
        return users;
    }
}
