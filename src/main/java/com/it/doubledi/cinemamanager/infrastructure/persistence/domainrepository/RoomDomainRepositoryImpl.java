package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.domain.repository.RowRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RowEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class RoomDomainRepositoryImpl extends AbstractDomainRepository<Room, RoomEntity, String> implements RoomRepository {
    private final RoomEntityRepository roomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final RowRepository rowRepository;
    private final RowEntityMapper rowEntityMapper;
    private final RowEntityRepository rowEntityRepository;

    public RoomDomainRepositoryImpl(RoomEntityRepository roomEntityRepository,
                                    RoomEntityMapper entityMapper,
                                    RowRepository rowRepository,
                                    RowEntityMapper rowEntityMapper,
                                    RowEntityRepository rowEntityRepository) {
        super(roomEntityRepository, entityMapper);
        this.roomEntityRepository = roomEntityRepository;
        this.roomEntityMapper = entityMapper;
        this.rowRepository = rowRepository;
        this.rowEntityMapper = rowEntityMapper;
        this.rowEntityRepository = rowEntityRepository;
    }

    @Override
    public Room getById(String id) {
        return this.findById(id).orElseThrow(()-> new ResponseException(NotFoundError.ROOM_NOT_FOUND));
    }

    @Override
    public Room save(Room domain) {
        super.save(domain);
        if (!CollectionUtils.isEmpty(domain.getRows())){
            this.rowRepository.saveALl(domain.getRows());
        }

        return domain;
    }

    @Override
    public List<Room> saveALl(List<Room> domains) {
        List<Row> rows = new ArrayList<>();
        domains.stream().forEach(d -> {
            if(!CollectionUtils.isEmpty(d.getRows())) {
                rows.addAll(d.getRows());
            }
        });
        if(CollectionUtils.isEmpty(rows)){
            this.rowRepository.saveALl(rows);
        }
        return super.saveALl(domains);
    }

    @Override
    protected List<Room> enrichList(List<Room> rooms) {
        List<RowEntity> rowEntities = rowEntityRepository.findRowByRoomIds(rooms.stream().map(Room::getId).collect(Collectors.toList()));
        List<Row> rows = rowEntityMapper.toDomain(rowEntities);
        for (Room room : rooms) {
            List<Row> rowTmp = rows.stream().filter(r -> Objects.equals(room.getId(), r.getRoomId())).collect(Collectors.toList());
            room.enrichRows(rowTmp);
        }
        return rooms;
    }
}
