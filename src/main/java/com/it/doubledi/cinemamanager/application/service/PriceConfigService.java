package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.domain.PriceConfig;

import java.util.List;

public interface PriceConfigService {
    List<PriceConfig> setUp(LocationPriceConfigRequest request);
}
