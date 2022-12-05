package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Drink;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DrinkEntityMapper extends EntityMapper<Drink, DrinkEntity> {
}
