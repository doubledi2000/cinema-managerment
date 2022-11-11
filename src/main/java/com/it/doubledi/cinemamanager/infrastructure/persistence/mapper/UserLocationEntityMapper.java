package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.UserLocation;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserLocationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserLocationEntityMapper extends EntityMapper<UserLocation, UserLocationEntity> {
}
