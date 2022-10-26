package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.PriceConfig;

import java.util.List;

public interface PriceConfigRepository extends DomainRepository<PriceConfig, String> {

    List<PriceConfig> enrichList(List<PriceConfig> priceConfigs);
}
