package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShowtimeEntityMapper extends EntityMapper<Showtime, ShowtimeEntity> {
}
