package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager.common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeOfFilmEntityMapper extends EntityMapper<TypeOfFilm, TypeOfFilmEntity> {
}
