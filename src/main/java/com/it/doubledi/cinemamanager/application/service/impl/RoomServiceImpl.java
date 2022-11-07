package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.mapper.util.PageableMapperUtil;
import com.it.doubledi.cinemamanager._common.persistence.support.SeqRepository;
import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.RoomService;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.domain.command.ChairUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoomUpdateCmd;
import com.it.doubledi.cinemamanager.domain.command.RowUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.RoomSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ChairEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ChairEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RowEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final String CHAR_ARRAY = "ABCDEFGHIJKLMNOPQ";
    private final RoomEntityRepository roomEntityRepository;
    private final RoomRepository roomRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final AutoMapper autoMapper;
    private final SeqRepository seqRepository;
    private final RowEntityMapper rowEntityMapper;
    private final RowEntityRepository rowEntityRepository;
    private final ChairEntityRepository chairEntityRepository;
    private final ChairEntityMapper chairEntityMapper;
    private final AutoMapperQuery autoMapperQuery;
    private final LocationEntityRepository locationEntityRepository;
    private final LocationEntityMapper locationEntityMapper;

    @Override
    public Room create(RoomCreateRequest request) {
        RoomCreateCmd cmd = autoMapper.from(request);
        Room room = new Room(cmd);
        List<Row> rows = getDefaultSetting(room);
        room.enrichRows(rows);
        roomRepository.save(room);
        return room;
    }

    @Override
    @Transactional
    public Room update(String id, RoomUpdateRequest request) {
        Room room = this.roomRepository.getById(id);
        RoomUpdateCmd cmd = this.autoMapper.from(request);
        List<Row> rows = room.getRows();
        room.update(cmd);
        for (RowUpdateCmd row : cmd.getRows()) {
            Optional<Row> rowTmpOptional = rows.stream().filter(r -> Objects.equals(r.getId(), row.getId())).findFirst();
            if (rowTmpOptional.isPresent()) {
                Row rowTmp = rowTmpOptional.get();
                List<Chair> chairs = rowTmp.getChairs();
                List<ChairUpdateCmd> chairUpdateCmds = row.getChairs();
                int count = 1;
                for (ChairUpdateCmd chairUpdateCmd : chairUpdateCmds) {
                    if (Objects.nonNull(chairUpdateCmd.getId())) {
                        Optional<Chair> chairTmp = chairs.stream().filter(c -> Objects.equals(c.getId(), chairUpdateCmd.getId())).findFirst();
                        if (chairTmp.isPresent()) {
                            Chair chair = chairTmp.get();
                            chair.setSerialOfChair(count);
                            chair.update(chairUpdateCmd);
                            if (Objects.equals(chair.getChairType(), ChairType.SWEET)) {
                                chair.setName(rowTmp.getName() + count + " - " + rowTmp.getName() + ++count);
                            } else {
                                chair.setName(rowTmp.getName() + count);
                            }
                            count++;
                        }
                    } else {
                        String chairName = Objects.equals(chairUpdateCmd.getChairType(), ChairType.SWEET)
                                ? rowTmp.getName() + count + " - " + rowTmp.getName() + (count + 1)
                                : rowTmp.getName() + count;
                        Chair chair = Chair.builder()
                                .id(IdUtils.nextId())
                                .code(seqRepository.generateChairCode())
                                .serialOfChair(count)
                                .chairType(chairUpdateCmd.getChairType())
                                .deleted(Boolean.FALSE)
                                .name(chairName)
                                .rowId(rowTmp.getId())
                                .build();
                        rowTmp.addChair(chair);
                        count++;
                    }
                }
            }
        }
        room = this.roomRepository.save(room);
        for (Row row : room.getRows()) {
            row.getChairs().retainAll(row.getChairs().stream().filter(r -> Objects.equals(r.getDeleted(), Boolean.FALSE)).collect(Collectors.toList()));
        }
        return room;
    }

    @Override
    public Room getById(String id) {
        Room room = roomRepository.getById(id);
        return room;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    @Override
    public Room duplicateRoom(String id) {
        Room room = this.roomRepository.getById(id);
        Room roomDuplicate = new Room(room);
        if (!CollectionUtils.isEmpty(room.getRows())) {
            List<Row> rowDuplicate = this.duplicateRow(roomDuplicate.getId(), room.getRows());
            roomDuplicate.enrichRows(rowDuplicate);
        }
        return roomRepository.save(roomDuplicate);
    }

    @Override
    public PageDTO<Room> search(RoomSearchRequest request) {
        RoomSearchQuery searchQuery = this.autoMapperQuery.toQuery(request);
        Long count = this.roomEntityRepository.count(searchQuery);
        if (count == 0) {
            return PageDTO.empty();
        }
        List<RoomEntity> roomEntities = this.roomEntityRepository.search(searchQuery);
        List<Room> rooms = this.roomEntityMapper.toDomain(roomEntities);
        List<String> locationIds = rooms.stream().map(Room::getLocationId).distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(locationIds)) {
            List<LocationEntity> locationEntities = this.locationEntityRepository.findByIds(locationIds);
            List<Location> locations = this.locationEntityMapper.toDomain(locationEntities);
            for (Room room : rooms) {
                locations.stream().filter(l -> Objects.equals(l.getId(), room.getLocationId())).findFirst().ifPresent(room::enrichLocation);
            }
        }
        return new PageDTO<>(rooms, searchQuery.getPageIndex(), searchQuery.getPageSize(), count);
    }

    @Override
    public PageDTO<Room> autoComplete(RoomSearchRequest request) {
        Pageable pageable = PageableMapperUtil.toPageable(request);
        Page<RoomEntity> roomEntityPage = this.roomEntityRepository.autoComplete(request.getLocationIds(), SqlUtils.encodeKeyword(request.getKeyword()), List.of(RoomStatus.ACTIVE), pageable);
        List<Room> rooms = this.roomEntityMapper.toDomain(roomEntityPage.getContent());
        return new PageDTO<>(rooms,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                roomEntityPage.getTotalElements());
    }

    private List<Row> getDefaultSetting(Room room) {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < room.getMaxRow(); i++) {

            Row row = Row.builder()
                    .roomId(room.getId())
                    .code(seqRepository.generateRowCode())
                    .name(CHAR_ARRAY.substring(i, i + 1))
                    .rowNumber(i + 1)
                    .id(IdUtils.nextId())
                    .deleted(Boolean.FALSE)
                    .build();
            List<Chair> chairs = new ArrayList<>();
            for (int j = 0; j < room.getMaxChairPerRow(); j++) {
                Chair chair = Chair.builder()
                        .id(IdUtils.nextId())
                        .rowId(row.getId())
                        .deleted(Boolean.FALSE)
                        .chairType(ChairType.NORMAL)
                        .serialOfChair(j)
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

    private List<Row> duplicateRow(String roomId, List<Row> rows) {
        List<Row> rowDuplicates = new ArrayList<>();
        for (Row row : rows) {
            Row rowTmp = Row.builder()
                    .roomId(roomId)
                    .code(seqRepository.generateRowCode())
                    .name(row.getName())
                    .rowNumber(row.getRowNumber())
                    .id(IdUtils.nextId())
                    .deleted(Boolean.FALSE)
                    .build();
            List<Chair> chairs = row.getChairs();
            List<Chair> chairDuplicate = new ArrayList<>();
            if (!CollectionUtils.isEmpty(chairs)) {
                for (Chair chair : chairs) {
                    Chair chairTmp = Chair.builder()
                            .id(IdUtils.nextId())
                            .rowId(rowTmp.getId())
                            .deleted(Boolean.FALSE)
                            .chairType(chair.getChairType())
                            .serialOfChair(chair.getSerialOfChair())
                            .code(seqRepository.generateChairCode())
                            .name(chair.getName())
                            .build();
                    chairDuplicate.add(chairTmp);
                }
            }
            rowTmp.enrichChairs(chairDuplicate);
            rowDuplicates.add(rowTmp);
        }
        return rowDuplicates;
    }
}
