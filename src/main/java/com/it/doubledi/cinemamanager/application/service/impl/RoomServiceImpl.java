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
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RowEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private final RowEntityMapper rowEntityMapper;
    private final RowEntityRepository rowEntityRepository;

    public RoomServiceImpl(RoomEntityRepository roomEntityRepository,
                           RoomRepository roomRepository,
                           RoomEntityMapper roomEntityMapper,
                           AutoMapper autoMapper,
                           SeqRepository seqRepository,
                           RowEntityMapper rowEntityMapper,
                           RowEntityRepository rowEntityRepository) {
        this.roomEntityRepository = roomEntityRepository;
        this.roomRepository = roomRepository;
        this.roomEntityMapper = roomEntityMapper;
        this.autoMapper = autoMapper;
        this.seqRepository = seqRepository;
        this.rowEntityMapper = rowEntityMapper;
        this.rowEntityRepository = rowEntityRepository;
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
        Room room = roomRepository.getById(id);
        List<RowEntity> rowEntities = this.rowEntityRepository.findRowByRoomId(id);
        if(CollectionUtils.isEmpty(rowEntities)) {
            List<Row> rows = this.rowEntityMapper.toDomain(rowEntities);
        }
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
            List<Chair> chairs = new ArrayList<>();
            for (int j = 0; j < room.getMaxChairPerRow();j++) {
                Chair chair = Chair.builder()
                        .id(IdUtils.nextId())
                        .rowId(row.getId())
                        .deleted(Boolean.FALSE)
                        .chairType(ChairType.NORMAL)
                        .serialOfChair(i)
                        .code(seqRepository.generateChairCode())
                        .name("A")
                        .build();
                chairs.add(chair);

            }
            row.enrichChairs(chairs);
            rows.add(row);
        }
        return rows;
    }

}
