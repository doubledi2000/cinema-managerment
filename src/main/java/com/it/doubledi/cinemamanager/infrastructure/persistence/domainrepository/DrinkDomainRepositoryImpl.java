package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Drink;
import com.it.doubledi.cinemamanager.domain.repository.DrinkRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DrinkDomainRepositoryImpl extends AbstractDomainRepository<Drink, DrinkEntity, String> implements DrinkRepository {

    public DrinkDomainRepositoryImpl(JpaRepository<DrinkEntity, String> jpaRepository, EntityMapper<Drink, DrinkEntity> entityMapper) {
        super(jpaRepository, entityMapper);
    }

    @Override
    public Drink getById(String id) {
        DrinkEntity drinkEntity = jpaRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.DRINK_NOT_FOUND));
        return entityMapper.toDomain(drinkEntity);
    }
}
