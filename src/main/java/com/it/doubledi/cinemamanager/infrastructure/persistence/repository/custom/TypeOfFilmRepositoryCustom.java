package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.TypeOfFilmSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;

import java.util.List;

public interface TypeOfFilmRepositoryCustom {
    List<TypeOfFilmEntity> search(TypeOfFilmSearchQuery searchQuery);

    Long count(TypeOfFilmSearchQuery searchQuery);
}
