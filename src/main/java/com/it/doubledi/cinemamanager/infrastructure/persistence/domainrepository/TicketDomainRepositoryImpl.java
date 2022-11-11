package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Ticket;
import com.it.doubledi.cinemamanager.domain.repository.TicketRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TicketEntity;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketDomainRepositoryImpl extends AbstractDomainRepository<Ticket, TicketEntity, String> implements TicketRepository {
    public TicketDomainRepositoryImpl(JpaRepository<TicketEntity, String> jpaRepository, EntityMapper<Ticket, TicketEntity> entityMapper) {
        super(jpaRepository, entityMapper);
    }

    @Override
    public Ticket getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.TICKET_NOT_FOUND));
    }
}
