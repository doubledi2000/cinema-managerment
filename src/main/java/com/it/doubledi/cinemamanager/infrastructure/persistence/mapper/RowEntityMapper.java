package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RowEntityMapper extends EntityMapper<Row, RowEntity> {
}
