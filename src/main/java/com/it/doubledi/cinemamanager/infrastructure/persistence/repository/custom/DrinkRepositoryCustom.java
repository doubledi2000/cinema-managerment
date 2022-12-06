package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.DrinkSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;

import java.util.List;

public interface DrinkRepositoryCustom {
    List<DrinkEntity> search(DrinkSearchQuery searchQuery);

    Long count(DrinkSearchQuery searchQuery);
}
