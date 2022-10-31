package com.it.doubledi.cinemamanager.application.mapper;

import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmSearchRequest;
import com.it.doubledi.cinemamanager.domain.query.LocationSearchQuery;
import com.it.doubledi.cinemamanager.domain.query.RoomSearchQuery;
import com.it.doubledi.cinemamanager.domain.query.TypeOfFilmSearchQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapperQuery {
    TypeOfFilmSearchQuery toQuery(TypeOfFilmSearchRequest request);

    LocationSearchQuery toQuery(LocationSearchRequest request);

    RoomSearchQuery toQuery(RoomSearchRequest request);
}
