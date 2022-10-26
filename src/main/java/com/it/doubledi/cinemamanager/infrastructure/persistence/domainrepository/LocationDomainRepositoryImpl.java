package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.repository.LocationRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;


@Service
public class LocationDomainRepositoryImpl extends AbstractDomainRepository<Location, LocationEntity, String> implements LocationRepository {
    private final LocationEntityRepository locationEntityRepository;
    private final LocationEntityMapper locationEntityMapper;

    public LocationDomainRepositoryImpl(LocationEntityRepository locationEntityRepository,
                                        LocationEntityMapper locationEntityMapper) {
        super(locationEntityRepository, locationEntityMapper);
        this.locationEntityRepository = locationEntityRepository;
        this.locationEntityMapper = locationEntityMapper;
    }

    @Override
    public Location getById(String id) {
        LocationEntity locationEntity = this.locationEntityRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.LOCATION_NOT_FOUND));
        return this.locationEntityMapper.toDomain(locationEntity);
    }
}
