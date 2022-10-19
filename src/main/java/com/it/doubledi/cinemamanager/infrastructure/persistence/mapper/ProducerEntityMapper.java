package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Producer;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProducerEntityMapper extends EntityMapper<Producer, ProducerEntity> {
}
