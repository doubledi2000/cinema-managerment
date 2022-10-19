package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.PriceByTime;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.repository.PriceByTimeRepository;
import com.it.doubledi.cinemamanager.domain.repository.PriceConfigRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceConfigEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceByTimeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.PriceConfigEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceByTimeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.PriceConfigEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    public PriceConfigDomainRepositoryImpl(PriceConfigEntityRepository priceConfigEntityRepository,
                                           PriceConfigEntityMapper priceConfigEntityMapper,
                                           PriceByTimeEntityRepository priceByTimeEntityRepository,
                                           PriceByTimeEntityMapper priceByTimeEntityMapper,
                                           PriceByTimeRepository priceByTimeRepository) {
        super(priceConfigEntityRepository, priceConfigEntityMapper);
        this.priceConfigEntityRepository = priceConfigEntityRepository;
        this.priceConfigEntityMapper = priceConfigEntityMapper;
        this.priceByTimeEntityRepository = priceByTimeEntityRepository;
        this.priceByTimeEntityMapper = priceByTimeEntityMapper;
        this.priceByTimeRepository = priceByTimeRepository;
    }

    @Override
    public PriceConfig getById(String id) {
        PriceConfigEntity priceConfigEntity = this.priceConfigEntityRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.PRICE_CONFIG_NOT_FOUND));
        return this.priceConfigEntityMapper.toDomain(priceConfigEntity);
    }

    @Override
    protected List<PriceConfig> enrichList(List<PriceConfig> priceConfigs) {
        if (CollectionUtils.isEmpty(priceConfigs)) {
            return new ArrayList<>();
        }
        List<String> priceConfigIds = priceConfigs.stream().map(PriceConfig::getId).collect(Collectors.toList());
        List<PriceByTimeEntity> priceByTimeEntities = this.priceByTimeEntityRepository.findAllByPriceConfigIds(priceConfigIds);

        List<PriceByTime> priceByTimes = this.priceByTimeEntityMapper.toDomain(priceByTimeEntities);
        for (PriceConfig priceConfig : priceConfigs) {
            List<PriceByTime> priceByTimesTmp = priceByTimes.stream().filter(p -> Objects.equals(p.getConfigPriceId(), priceConfig.getId())).collect(Collectors.toList());
            priceConfig.enrichPriceByTime(priceByTimesTmp);
        }

        return priceConfigs;
    }

    @Override
    public PriceConfig save(PriceConfig domain) {
        if (!CollectionUtils.isEmpty(domain.getPriceByTimes())) {
            this.priceByTimeRepository.saveALl(domain.getPriceByTimes());
        }
        return super.save(domain);
    }

    @Override
    public List<PriceConfig> saveALl(List<PriceConfig> domains) {
        if (CollectionUtils.isEmpty(domains)) {
            return new ArrayList<>();
        }
        List<PriceByTime> priceByTimes = new ArrayList<>();
        for (PriceConfig domain : domains) {
            if (!CollectionUtils.isEmpty(domain.getPriceByTimes())) {
                priceByTimes.addAll(domain.getPriceByTimes());
            }
        }
        if (CollectionUtils.isEmpty(priceByTimes)) {
            this.priceByTimeRepository.saveALl(priceByTimes);
        }
        return super.saveALl(domains);
    }
}
