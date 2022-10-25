package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.PriceConfigService;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.command.ConfigPriceCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.LocationPriceConfigCmd;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PriceConfigServiceImpl implements PriceConfigService {

    private final PriceConfigRepository priceConfigRepository;
    private final AutoMapper autoMapper;

    @Override
    public List<PriceConfig> setUp(LocationPriceConfigRequest request) {
        LocationPriceConfigCmd cmd = autoMapper.from(request);
        List<PriceConfig> priceConfigs = new ArrayList<>();
        for (ConfigPriceCreateCmd configPriceCreateRequest : cmd.getConfigPriceCreateRequests()) {
            PriceConfig priceConfig = new PriceConfig(cmd.getLocationId(), configPriceCreateRequest, PriceConfigType.TICKET);
            priceConfig.enrichPriceByTime(priceConfig.getPriceByTimeFromRequest(configPriceCreateRequest));
        }
        return null;
    }
}
