package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.domain.UserLocation;
import com.it.doubledi.cinemamanager.domain.repository.UserRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserLocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.UserEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.UserLocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserLocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public UserDomainRepositoryImpl(UserEntityRepository userEntityRepository,
                                    UserEntityMapper userEntityMapper,
                                    UserLocationEntityRepository userLocationEntityRepository,
                                    UserLocationEntityMapper userLocationEntityMapper,
                                    LocationEntityRepository locationEntityRepository,
                                    LocationEntityMapper locationEntityMapper) {
        super(userEntityRepository, userEntityMapper);
        this.userEntityRepository = userEntityRepository;
        this.userEntityMapper = userEntityMapper;
        this.userLocationEntityRepository = userLocationEntityRepository;
        this.userLocationEntityMapper = userLocationEntityMapper;
        this.locationEntityRepository = locationEntityRepository;
        this.locationEntityMapper = locationEntityMapper;
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
        domains.forEach(e -> {
            if (!CollectionUtils.isEmpty(e.getLocations())) {
                userLocations.addAll(e.getLocations());
            }
        });
        this.userLocationEntityRepository.saveAll(this.userLocationEntityMapper.toEntity(userLocations));
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


        for (User user : users) {
            List<String> locationIdsTmp = userLocations.stream()
                    .filter(u -> Objects.equals(user.getId(), u.getUserId()))
                    .map(UserLocation::getBuildingId)
                    .collect(Collectors.toList());

            user.enrichLocationIds(locationIdsTmp);
            List<UserLocation> userLocationsTmp = userLocations.stream().filter(u -> Objects.equals(u.getUserId(), user.getId())).collect(Collectors.toList());
            user.enrichUserLocation(userLocationsTmp);
        }
        return users;
    }
}
