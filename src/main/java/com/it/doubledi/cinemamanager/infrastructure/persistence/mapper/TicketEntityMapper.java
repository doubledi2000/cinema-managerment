package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Ticket;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TicketEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketEntityMapper extends EntityMapper<Ticket, TicketEntity> {
}
