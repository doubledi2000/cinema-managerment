package com.it.doubledi.cinemamanager.application.mapper;

import com.it.doubledi.cinemamanager.application.dto.request.*;
import com.it.doubledi.cinemamanager.domain.command.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapper {
    RoomCreateCmd from(RoomCreateRequest request);

    RoomUpdateCmd from(RoomUpdateRequest request);

    TypeOfFilmCreateCmd from(TypeOfFilmCreateRequest request);

    TypeOfFilmUpdateCmd from(TypeOfFilmUpdateRequest request);

    PriceByTimeCreateCmd from(PriceByTimeCreateRequest request);

    PriceConfigCreateCmd from(PriceConfigCreateRequest request);

    LocationPriceConfigCmd from(LocationPriceConfigRequest request);

    LocationCreateCmd from(LocationCreateRequest request);

    FilmCreateCmd from(FilmCreateRequest request);

    ShowtimeCreateCmd from(ShowtimeCreateRequest request);

    ChairUpdateCmd from(ChairUpdateRequest request);

    RowUpdateCmd from(RowUpdateRequest request);

    ProducerCreateCmd from(ProducerCreateRequest request);

    ProducerUpdateCmd from(ProducerUpdateRequest request);

    FilmScheduleCreateCmd from(FilmScheduleCreateRequest request);

    UserCreateCmd from(UserCreateRequest request);

    UserUpdateCmd from(UserUpdateRequest request);

    RoleCreateCmd from(RoleCreateRequest request);

    RoleUpdateCmd from(RoleUpdateRequest request);

    RolePermittedCmd from(RolePermittedRequest request);

    TicketPriceConfigUpdateCmd from(TicketPriceConfigUpdateRequest request);

    DrinkCreateCmd from(DrinkCreateRequest request);

    DrinkUpdateCmd from(DrinkUpdateRequest request);

    InvoiceCreateCmd from(InvoiceCreateRequest request);

    RolePermissionCmd from(RolePermissionRequest request);

    UpdateProfileCmd from(UpdateProfileRequest request);
}
