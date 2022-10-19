package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.persistence.support.SeqRepository;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.RoomService;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.domain.Seat;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ChairEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.SeatEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ChairEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.SeatEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final String CHAR_ARRAY = "ABCDEFGHIJKLMNOPQ";
    private final RoomEntityRepository roomEntityRepository;
    private final RoomRepository roomRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final AutoMapper autoMapper;
    private final SeqRepository seqRepository;
    private final SeatEntityMapper chairEntityMapper;
    private final RowEntityMapper rowEntityMapper;

    public RoomServiceImpl(RoomEntityRepository roomEntityRepository,
                           RoomRepository roomRepository,
                           RoomEntityMapper roomEntityMapper,
                           AutoMapper autoMapper,
                           SeqRepository seqRepository, SeatEntityMapper chairEntityMapper, RowEntityMapper rowEntityMapper) {
        this.roomEntityRepository = roomEntityRepository;
        this.roomRepository = roomRepository;
        this.roomEntityMapper = roomEntityMapper;
        this.autoMapper = autoMapper;
        this.seqRepository = seqRepository;
        this.chairEntityMapper = chairEntityMapper;
        this.rowEntityMapper = rowEntityMapper;
    }

    @Override
    public Room create(RoomCreateRequest request) {
        RoomCreateCmd cmd = autoMapper.from(request);
        Room room = new Room(cmd);
        RoomEntity roomEntity = roomEntityMapper.toEntity(room);

        if(cmd.getDefaultSetting()){
            List<Row> rows = getDefaultSetting(room);
            room.enrichRows(rows);;
        }
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room update(String id, RoomUpdateRequest request) {
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

    private List<Row> getDefaultSetting(Room room) {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < room.getMaxRow(); i++) {

            Row row = Row.builder()
                    .roomId(room.getId())
                    .code(seqRepository.generateRowCode())
                    .name(CHAR_ARRAY.substring(i, i+1))
                    .rowNumber(i+1)
                    .id(IdUtils.nextId())
                    .build();
            List<Seat> seats = new ArrayList<>();
            for (int j = 0; j < room.getMaxChairPerRow();j++) {
                Seat chair = Seat.builder()
                        .id(IdUtils.nextId())
                        .rowId(row.getId())
                        .deleted(Boolean.FALSE)
                        .chairType(ChairType.NORMAL)
                        .serialOfChair(i)
                        .code(seqRepository.generateChairCode())
                        .name("A")
                        .build();
                SeatEntity chairEntity = this.chairEntityMapper.toEntity(chair);
                seats.add(chair);

            }
            List<SeatEntity> chairEntities = this.chairEntityMapper.toEntity(seats);
            row.enrichChairs(seats);
            rows.add(row);
        }
        List<RowEntity> rowEntities = rowEntityMapper.toEntity(rows);
        return rows;
    }

}
