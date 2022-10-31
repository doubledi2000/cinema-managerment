package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.mapper.util.PageableMapperUtil;
import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.LocationService;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.command.LocationCreateCmd;
import com.it.doubledi.cinemamanager.domain.query.LocationSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.LocationRepository;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationEntityRepository locationEntityRepository;
    private final AutoMapper autoMapper;
    private final PriceConfigRepository priceConfigRepository;
    private final AutoMapperQuery autoMapperQuery;
    private final LocationEntityMapper locationEntityMapper;

    @Override
    @Transactional
    public Location create(LocationCreateRequest request) {
        LocationCreateCmd cmd = this.autoMapper.from(request);
        Location location = new Location(cmd);
        List<PriceConfig> priceConfigs = new ArrayList<>();
        for (int i = 0; i <7;i++){
            PriceConfig priceConfig = new PriceConfig(location.getId(), i);
            priceConfigs.add(priceConfig);
        }
        priceConfigRepository.saveALl(priceConfigs);
        this.locationRepository.save(location);
        return location;
    }

    @Override
    @Transactional
    public Location update(LocationCreateRequest request) {
        return null;
    }

    @Override
    public Location getById(String id) {
        return this.locationRepository.getById(id);
    }

    @Override
    @Transactional
    public void active(String id) {
        Location location = this.locationRepository.getById(id);
        location.active();
        this.locationRepository.save(location);
    }

    @Override
    @Transactional
    public void inactive(String id) {
        Location location = this.locationRepository.getById(id);
        location.inactive();
        this.locationRepository.save(location);
    }

    @Override
    public PageDTO<Location> search(LocationSearchRequest request) {
        LocationSearchQuery searchQuery = this.autoMapperQuery.toQuery(request);
        Long count = locationEntityRepository.count(searchQuery);
        if(Objects.equals(count, 0L)) {
            return PageDTO.empty();
        }
        List<LocationEntity> locationEntities = this.locationEntityRepository.search(searchQuery);
        List<Location> locations = this.locationEntityMapper.toDomain(locationEntities);

        return new PageDTO<>(locations, searchQuery.getPageIndex(), searchQuery.getPageIndex(), count);
    }

    @Override
    public PageDTO<Location> autoComplete(LocationSearchRequest request) {
        Pageable pageable = PageableMapperUtil.toPageable(request);
        Page<LocationEntity> locationEntityPage = this.locationEntityRepository.autoComplete(SqlUtils.encodeKeyword(request.getKeyword()), List.of(LocationStatus.ACTIVE), pageable);
        List<LocationEntity> locationEntities = locationEntityPage.getContent();
        List<Location> locations = this.locationEntityMapper.toDomain(locationEntities);
        return new PageDTO<>(locations, pageable.getPageNumber(), pageable.getPageSize(), locationEntityPage.getTotalElements());
    }
}
