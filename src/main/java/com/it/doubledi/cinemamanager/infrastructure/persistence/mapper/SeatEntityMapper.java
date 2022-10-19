package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Seat;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.SeatEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatEntityMapper extends EntityMapper<Seat, SeatEntity> {
}
