package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Price;
import com.it.doubledi.cinemamanager.domain.PriceByTime;
import com.it.doubledi.cinemamanager.domain.repository.PriceByTimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceByTimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceByTimeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PriceByTimeDomainRepositoryImpl extends AbstractDomainRepository<PriceByTime, PriceByTimeEntity, String> implements PriceByTimeRepository {

    private final PriceByTimeEntityRepository priceByTimeEntityRepository;
    private final PriceByTimeEntityMapper priceByTimeEntityMapper;
    private final PriceEntityRepository priceEntityRepository;
    private final PriceEntityMapper priceEntityMapper;

    public PriceByTimeDomainRepositoryImpl(PriceByTimeEntityRepository priceByTimeEntityRepository,
                                           PriceByTimeEntityMapper priceByTimeEntityMapper,
                                           PriceEntityRepository priceEntityRepository,
                                           PriceEntityMapper priceEntityMapper) {
        super(priceByTimeEntityRepository, priceByTimeEntityMapper);
        this.priceByTimeEntityRepository = priceByTimeEntityRepository;
        this.priceByTimeEntityMapper = priceByTimeEntityMapper;
        this.priceEntityRepository = priceEntityRepository;
        this.priceEntityMapper = priceEntityMapper;
    }

    @Override
    public PriceByTime getById(String id) {
        PriceByTimeEntity priceEntity = this.priceByTimeEntityRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.PRICE_BY_TIME_NOT_FOUND));
        return this.priceByTimeEntityMapper.toDomain(priceEntity);
    }

    @Override
    public List<PriceByTime> enrichList(List<PriceByTime> priceByTimes) {
        if (CollectionUtils.isEmpty(priceByTimes)) {
            return new ArrayList<>();
        }
        List<String> priceByTimeIds = priceByTimes.stream().map(PriceByTime::getId).collect(Collectors.toList());
        List<PriceEntity> priceEntities = this.priceEntityRepository.getAllByPriceByTimeIds(priceByTimeIds);
        List<Price> prices = this.priceEntityMapper.toDomain(priceEntities);
        for (PriceByTime priceByTime : priceByTimes) {
            List<Price> priceTmp = prices.stream()
                    .filter(p -> Objects.equals(p.getPriceByTimeId(), priceByTime.getId()))
                    .collect(Collectors.toList());
            priceByTime.enrichPrices(priceTmp);
        }
        return priceByTimes;
    }
}
