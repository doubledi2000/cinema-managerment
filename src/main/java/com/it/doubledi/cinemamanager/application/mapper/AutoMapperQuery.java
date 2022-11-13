package com.it.doubledi.cinemamanager.application.mapper;

import com.it.doubledi.cinemamanager.application.dto.request.*;
import com.it.doubledi.cinemamanager.domain.query.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapperQuery {
    TypeOfFilmSearchQuery toQuery(TypeOfFilmSearchRequest request);

    LocationSearchQuery toQuery(LocationSearchRequest request);

    RoomSearchQuery toQuery(RoomSearchRequest request);

    FilmSearchQuery toQuery(FilmSearchRequest request);

    ProducerSearchQuery toQuery(ProducerSearchRequest request);

    UserSearchQuery toQuery(UserSearchRequest request);

    RoleSearchQuery toQuery(RoleSearchRequest request);
}
