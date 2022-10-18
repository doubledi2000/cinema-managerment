package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager.common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomDomainRepositoryImpl extends AbstractDomainRepository<Room, RoomEntity, String> implements RoomRepository {

    public RoomDomainRepositoryImpl(RoomEntityRepository roomRepository, RoomEntityMapper entityMapper) {
        super(roomRepository, entityMapper);
    }

    @Override
    public Room getById(String id) {
        return this.findById(id).orElse(null);
    }
}
