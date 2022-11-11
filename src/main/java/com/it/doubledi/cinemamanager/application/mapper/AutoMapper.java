package com.it.doubledi.cinemamanager.application.mapper;

import com.it.doubledi.cinemamanager.application.dto.request.ChairUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmScheduleCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.application.dto.request.PriceByTimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.PriceConfigCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RowUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.domain.command.ChairUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.FilmCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.FilmScheduleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.LocationCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.LocationPriceConfigCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceConfigCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ProducerCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ProducerUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoomUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.RowUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.TypeOfFilmCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.TypeOfFilmUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.UserCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.UserUpdateCmd;
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
}
