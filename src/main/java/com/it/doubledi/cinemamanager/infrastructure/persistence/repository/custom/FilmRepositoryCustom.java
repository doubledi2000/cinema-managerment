package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.FilmSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;

import java.util.List;

public interface FilmRepositoryCustom {

    List<FilmEntity> search(FilmSearchQuery searchQuery);

    Long count(FilmSearchQuery searchQuery);
}
