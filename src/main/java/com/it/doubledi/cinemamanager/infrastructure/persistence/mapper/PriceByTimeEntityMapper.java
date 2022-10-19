package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.PriceByTime;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceByTimeEntityMapper extends EntityMapper<PriceByTime, PriceByTimeEntity> {
}
