package com.it.doubledi.cinemamanager.application.mapper;

import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoomUpdateCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapper {
    RoomCreateCmd from(RoomCreateRequest request);

    RoomUpdateCmd from(RoomUpdateRequest request);
}
