package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager.common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomEntityMapper extends EntityMapper<Room, RoomEntity> {
}
