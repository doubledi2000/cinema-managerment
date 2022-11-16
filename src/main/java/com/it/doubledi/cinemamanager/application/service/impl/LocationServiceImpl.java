package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.util.PageableMapperUtil;
import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TicketPriceConfigUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.response.LocationPriceConfigResponse;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.LocationService;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.PriceByTime;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.command.LocationCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.TicketPriceConfigUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.LocationSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.LocationRepository;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceConfigEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceConfigEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceConfigEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationEntityRepository locationEntityRepository;
    private final AutoMapper autoMapper;
    private final PriceConfigRepository priceConfigRepository;
    private final AutoMapperQuery autoMapperQuery;
    private final LocationEntityMapper locationEntityMapper;
    private final PriceConfigEntityRepository priceConfigEntityRepository;
    private final PriceConfigEntityMapper priceConfigEntityMapper;

    @Override
    @Transactional
    public Location create(LocationCreateRequest request) {
        LocationCreateCmd cmd = this.autoMapper.from(request);
        Location location = new Location(cmd);
        List<PriceConfig> priceConfigs = new ArrayList<>();
        for (int i = 0; i <7;i++){
            PriceConfig priceConfig = new PriceConfig(location.getId(), i, Boolean.FALSE);
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
    public LocationPriceConfigResponse getAllPriceConfigNotSpecial(String locationId) {
        Location location = this.locationRepository.getById(locationId);
        List<PriceConfigEntity> priceConfigEntities = this.priceConfigEntityRepository.getAllByLocationId(locationId, Boolean.FALSE);
        List<PriceConfig> priceConfigs = this.priceConfigEntityMapper.toDomain(priceConfigEntities);
        this.priceConfigRepository.enrichList(priceConfigs);

        return LocationPriceConfigResponse.builder()
                .locationId(location.getId())
                .locationCode(location.getCode())
                .locationName(location.getName())
                .priceConfigs(priceConfigs)
                .build();
    }

    @Override
    @Transactional
    public void updatePriceConfig(TicketPriceConfigUpdateRequest request) {
        TicketPriceConfigUpdateCmd cmd = this.autoMapper.from(request);
        Location location = this.locationRepository.getById(cmd.getId());
        List<PriceConfigEntity> priceConfigEntities = this.priceConfigEntityRepository.getAllByLocationId(location.getId(), Boolean.FALSE);
        List<PriceConfig> priceConfigs = this.priceConfigEntityMapper.toDomain(priceConfigEntities);
        this.priceConfigRepository.enrichList(priceConfigs);
        //check time sequence
        for (PriceConfig configPrice : request.getConfigPrices()) {
            List<PriceByTime> priceByTimes = configPrice.getPriceByTimes().stream().sorted(Comparator.comparing(PriceByTime::getStartAt)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(priceByTimes)) {
               throw new ResponseException(BadRequestError.PRICE_BY_TIME_REQUIRED);
            }

            if(!Objects.equals(priceByTimes.get(0).getStartAt(), Constant.MIN_START_AT)
                    || !Objects.equals(priceByTimes.get(priceByTimes.size() - 1).getEndAt(), Constant.MAX_START_AT) ) {
                throw new ResponseException(BadRequestError.PRICE_BY_TIME_MUST_COVER_FULL_DAY);
            }
            int endAt = 0;
            for (PriceByTime priceByTime : priceByTimes) {
                if(Objects.equals(priceByTime.getStartAt(), 0)) {
                    endAt = priceByTime.getEndAt();
                    continue;
                }
                if(!Objects.equals(endAt, priceByTime.getStartAt() - 1)) {
                    throw new ResponseException(BadRequestError.PRICE_BY_TIME_MUST_COVER_FULL_DAY);
                }
                endAt = priceByTime.getEndAt();
            }
        }

        for (PriceConfig priceConfig : priceConfigs) {
            Optional<PriceConfig> priceConfigOptional = request.getConfigPrices().stream().filter(o -> Objects.equals(o.getId(), priceConfig.getId())).findFirst();
            if(priceConfigOptional.isPresent()) {
                priceConfig.delete();
                priceConfig.updatePriceList(priceConfigOptional.get());
            }
        }
        this.priceConfigRepository.saveALl(priceConfigs);
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
