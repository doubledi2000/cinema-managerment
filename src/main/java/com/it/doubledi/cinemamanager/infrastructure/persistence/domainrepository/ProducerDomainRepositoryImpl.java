package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Producer;
import com.it.doubledi.cinemamanager.domain.repository.ProducerRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ProducerEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ProducerEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;

@Service
public class ProducerDomainRepositoryImpl extends AbstractDomainRepository<Producer, ProducerEntity, String> implements ProducerRepository {

    public ProducerDomainRepositoryImpl(ProducerEntityRepository producerEntityRepository, ProducerEntityMapper producerEntityMapper) {
        super(producerEntityRepository, producerEntityMapper);
    }

    @Override
    public Producer getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.PRODUCER_NOT_FOUND));
    }
}
