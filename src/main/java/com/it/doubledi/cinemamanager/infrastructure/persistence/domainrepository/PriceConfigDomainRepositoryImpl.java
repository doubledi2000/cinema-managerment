package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Price;
import com.it.doubledi.cinemamanager.domain.PriceByTime;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.repository.PriceByTimeRepository;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceConfigEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceByTimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceConfigEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceByTimeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceConfigEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PriceConfigDomainRepositoryImpl extends AbstractDomainRepository<PriceConfig, PriceConfigEntity, String> implements PriceConfigRepository {

    private final PriceConfigEntityRepository priceConfigEntityRepository;
    private final PriceConfigEntityMapper priceConfigEntityMapper;
    private final PriceByTimeEntityRepository priceByTimeEntityRepository;
    private final PriceByTimeEntityMapper priceByTimeEntityMapper;
    private final PriceByTimeRepository priceByTimeRepository;
    private final PriceEntityRepository priceEntityRepository;
    private final PriceEntityMapper priceEntityMapper;

    public PriceConfigDomainRepositoryImpl(PriceConfigEntityRepository priceConfigEntityRepository,
                                           PriceConfigEntityMapper priceConfigEntityMapper,
                                           PriceByTimeEntityRepository priceByTimeEntityRepository,
                                           PriceByTimeEntityMapper priceByTimeEntityMapper,
                                           PriceByTimeRepository priceByTimeRepository,
                                           PriceEntityRepository priceEntityRepository,
                                           PriceEntityMapper priceEntityMapper) {
        super(priceConfigEntityRepository, priceConfigEntityMapper);
        this.priceConfigEntityRepository = priceConfigEntityRepository;
        this.priceConfigEntityMapper = priceConfigEntityMapper;
        this.priceByTimeEntityRepository = priceByTimeEntityRepository;
        this.priceByTimeEntityMapper = priceByTimeEntityMapper;
        this.priceByTimeRepository = priceByTimeRepository;
        this.priceEntityRepository = priceEntityRepository;
        this.priceEntityMapper = priceEntityMapper;
    }

    @Override
    public PriceConfig getById(String id) {
        PriceConfigEntity priceConfigEntity = this.priceConfigEntityRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.PRICE_CONFIG_NOT_FOUND));
        return this.priceConfigEntityMapper.toDomain(priceConfigEntity);
    }

    @Override
    public List<PriceConfig> enrichList(List<PriceConfig> priceConfigs) {
        if (CollectionUtils.isEmpty(priceConfigs)) {
            return new ArrayList<>();
        }
        List<String> priceConfigIds = priceConfigs.stream().map(PriceConfig::getId).collect(Collectors.toList());
        List<PriceByTimeEntity> priceByTimeEntities = this.priceByTimeEntityRepository.findAllByPriceConfigIds(priceConfigIds);

        List<PriceByTime> priceByTimes = this.priceByTimeEntityMapper.toDomain(priceByTimeEntities);
        this.priceByTimeRepository.enrichList(priceByTimes);
        for (PriceConfig priceConfig : priceConfigs) {
            List<PriceByTime> priceByTimesTmp = priceByTimes.stream()
                    .filter(p -> Objects.equals(p.getPriceConfigId(), priceConfig.getId()))
                    .sorted(Comparator.comparing(PriceByTime::getStartAt))
                    .collect(Collectors.toList());
            priceConfig.enrichPriceByTime(priceByTimesTmp);
        }
        return priceConfigs;
    }

    @Override
    @Transactional
    public PriceConfig save(PriceConfig domain) {
        if (!CollectionUtils.isEmpty(domain.getPriceByTimes())) {
            this.priceByTimeRepository.saveALl(domain.getPriceByTimes());
        }
        return super.save(domain);
    }

    @Override
    @Transactional
    public List<PriceConfig> saveALl(List<PriceConfig> domains) {
        if (CollectionUtils.isEmpty(domains)) {
            return new ArrayList<>();
        }
        List<PriceByTime> priceByTimes = new ArrayList<>();
        List<Price> prices = new ArrayList<>();
        for (PriceConfig domain : domains) {
            if (!CollectionUtils.isEmpty(domain.getPriceByTimes())) {
                priceByTimes.addAll(domain.getPriceByTimes());
            }
        }
        for (PriceByTime priceByTime : priceByTimes) {
            if(!CollectionUtils.isEmpty(priceByTime.getPrices())){
                prices.addAll(priceByTime.getPrices());
            }
        }
        if(!CollectionUtils.isEmpty(prices)) {
            List<PriceEntity> priceEntities = this.priceEntityMapper.toEntity(prices);
            this.priceEntityRepository.saveAll(priceEntities);
        }
        if (!CollectionUtils.isEmpty(priceByTimes)) {
            List<PriceByTimeEntity> priceByTimeEntities = this.priceByTimeEntityMapper.toEntity(priceByTimes);
            this.priceByTimeEntityRepository.saveAll(priceByTimeEntities);
        }
        List<PriceConfigEntity> priceConfigEntities = this.priceConfigEntityMapper.toEntity(domains);
        this.priceConfigEntityRepository.saveAll(priceConfigEntities);
        return domains;
    }
}
