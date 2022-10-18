package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.RoomService;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomEntityRepository roomEntityRepository;
    private final RoomRepository roomRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final AutoMapper autoMapper;

    public RoomServiceImpl(RoomEntityRepository roomEntityRepository,
                           RoomRepository roomRepository,
                           RoomEntityMapper roomEntityMapper,
                           AutoMapper autoMapper) {
        this.roomEntityRepository = roomEntityRepository;
        this.roomRepository = roomRepository;
        this.roomEntityMapper = roomEntityMapper;
        this.autoMapper = autoMapper;
    }

    @Override
    public Room create(RoomCreateRequest request) {
        RoomCreateCmd cmd = autoMapper.from(request);
        Room room = new Room(cmd);
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room update(String id, RoomCreateRequest request) {
        return null;
    }

    @Override
    public Room getById(String id) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    @Override
    public Room duplicateRoom(String id, RoomCreateRequest request) {
        return null;
    }
}
