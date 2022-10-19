package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceConfigEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceConfigEntityMapper extends EntityMapper<PriceConfig, PriceConfigEntity> {
}
