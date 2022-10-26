package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.PriceByTime;

import java.util.List;

public interface PriceByTimeRepository extends DomainRepository<PriceByTime, String> {

    public List<PriceByTime> enrichList(List<PriceByTime> priceByTimes);
}
