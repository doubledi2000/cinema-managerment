package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.PriceConfigService;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.command.LocationPriceConfigCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceConfigCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceConfigEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceConfigEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceConfigEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriceConfigServiceImpl implements PriceConfigService {

    private final PriceConfigRepository priceConfigRepository;
    private final PriceConfigEntityRepository priceConfigEntityRepository;
    private final PriceConfigEntityMapper priceConfigEntityMapper;
    private final AutoMapper autoMapper;

    @Override
    public List<PriceConfig> setUp(LocationPriceConfigRequest request) {
        LocationPriceConfigCmd cmd = autoMapper.from(request);
        List<PriceConfigCreateCmd> priceConfigCreateCmds = cmd.getPriceConfigs();
        List<PriceConfigEntity> priceConfigEntities = this.priceConfigEntityRepository.getAllPriceConfigs();
        List<PriceConfig> priceConfigs = priceConfigEntityMapper.toDomain(priceConfigEntities);
        this.priceConfigRepository.enrichList(priceConfigs);
        for (PriceConfig priceConfig : priceConfigs) {
            Optional<PriceConfigCreateCmd> cmdTmp = priceConfigCreateCmds.stream().filter(p -> Objects.equals(p.getId(), priceConfig.getId())).findFirst();
            cmdTmp.ifPresent(priceConfig::update);

        }
        return null;
    }

    @Override
    public List<PriceConfig> demo() {
        PriceConfig priceConfig = new PriceConfig("11", DayOfWeek.MONDAY.getValue());
        List<PriceConfig> priceConfigs = new ArrayList<>();
        priceConfigs.add(priceConfig);
        priceConfigRepository.saveALl(priceConfigs);
        return List.of(priceConfig);
    }

    @Override
    public List<PriceConfig> getAllPriceConfigNotSpecial(String locationId) {
        List<PriceConfigEntity> priceConfigEntities = this.priceConfigEntityRepository.getAllByLocationId(locationId);
        List<PriceConfig> priceConfigs = this.priceConfigEntityMapper.toDomain(priceConfigEntities);
        this.priceConfigRepository.enrichList(priceConfigs);
        return priceConfigs;
    }

}
