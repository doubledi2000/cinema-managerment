package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.FilmProducer;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmProducerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmProducerEntityMapper extends EntityMapper<FilmProducer, FilmProducerEntity> {
}
