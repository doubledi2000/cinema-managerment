package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Seat;
import com.it.doubledi.cinemamanager.domain.repository.SeatRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.SeatEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.SeatEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.SeatEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatDomainRepositoryImpl extends AbstractDomainRepository<Seat, SeatEntity, String> implements SeatRepository {

    public SeatDomainRepositoryImpl(SeatEntityRepository seatEntityRepository, SeatEntityMapper seatEntityMapper) {
        super(seatEntityRepository, seatEntityMapper);
    }

    @Override
    public Seat getById(String id) {
        return null;
    }
}
