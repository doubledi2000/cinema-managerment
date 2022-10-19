package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.domain.repository.ChairRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ChairEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ChairEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ChairEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;

@Service
public class ChairDomainRepositoryImpl extends AbstractDomainRepository<Chair, ChairEntity, String> implements ChairRepository {
    private final ChairEntityRepository chairEntityRepository;
    private final ChairEntityMapper chairEntityMapper;

    public ChairDomainRepositoryImpl(ChairEntityRepository chairEntityRepository, ChairEntityMapper chairEntityMapper) {
        super(chairEntityRepository, chairEntityMapper);
        this.chairEntityRepository = chairEntityRepository;
        this.chairEntityMapper = chairEntityMapper;
    }


    @Override
    public Chair getById(String id) {
        return this.findById(id).orElseThrow(()->new ResponseException(NotFoundError.CHAIR_NOT_FOUND));
    }
}
