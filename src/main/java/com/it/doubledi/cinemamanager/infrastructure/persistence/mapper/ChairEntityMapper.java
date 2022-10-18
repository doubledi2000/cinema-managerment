package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager.common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ChairEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChairEntityMapper extends EntityMapper<Chair, ChairEntity> {
}
