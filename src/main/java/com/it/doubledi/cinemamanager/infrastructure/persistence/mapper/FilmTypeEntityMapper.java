package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.FilmType;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmTypeEntityMapper extends EntityMapper<FilmType, FilmTypeEntity> {
}
