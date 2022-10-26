package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.LocationService;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.command.LocationCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.LocationRepository;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationEntityRepository locationEntityRepository;
    private final AutoMapper autoMapper;
    private final PriceConfigRepository priceConfigRepository;

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
    public Location update(LocationCreateRequest request) {
        return null;
    }

    @Override
    public Location getById(String id) {
        return null;
    }
}
