package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmEntityMapper extends EntityMapper<Film, FilmEntity> {
}
