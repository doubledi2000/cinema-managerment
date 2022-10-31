package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.domain.repository.LocationRepository;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import com.it.doubledi.cinemamanager.domain.repository.RowRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ChairEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ChairEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RoomEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ChairEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RoomEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RowEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoomDomainRepositoryImpl extends AbstractDomainRepository<Room, RoomEntity, String> implements RoomRepository {
    private final RoomEntityRepository roomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final RowRepository rowRepository;
    private final RowEntityMapper rowEntityMapper;
    private final RowEntityRepository rowEntityRepository;
    private final ChairEntityRepository chairEntityRepository;
    private final ChairEntityMapper chairEntityMapper;
    private final LocationRepository locationRepository;

    public RoomDomainRepositoryImpl(RoomEntityRepository roomEntityRepository,
                                    RoomEntityMapper entityMapper,
                                    RowRepository rowRepository,
                                    RowEntityMapper rowEntityMapper,
                                    RowEntityRepository rowEntityRepository,
                                    ChairEntityRepository chairEntityRepository,
                                    ChairEntityMapper chairEntityMapper,
                                    LocationRepository locationRepository) {
        super(roomEntityRepository, entityMapper);
        this.roomEntityRepository = roomEntityRepository;
        this.roomEntityMapper = entityMapper;
        this.rowRepository = rowRepository;
        this.rowEntityMapper = rowEntityMapper;
        this.rowEntityRepository = rowEntityRepository;
        this.chairEntityRepository = chairEntityRepository;
        this.chairEntityMapper = chairEntityMapper;
        this.locationRepository = locationRepository;
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
        List<ChairEntity> chairEntities = this.chairEntityRepository.getAllChairByRowIds(rows.stream().map(Row::getId).collect(Collectors.toList()));
        List<Chair> chairs = this.chairEntityMapper.toDomain(chairEntities);
        List<Location> locations = this.locationRepository.findAllByIds(rooms.stream().map(Room::getLocationId).collect(Collectors.toList()));
        for (Room room : rooms) {
            List<Row> rowTmp = rows.stream().filter(r -> Objects.equals(room.getId(), r.getRoomId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(rowTmp)) {
                for (Row row : rowTmp) {
                    List<Chair> chairTmp = chairs.stream().filter(c->Objects.equals(c.getRowId(), row.getId())).collect(Collectors.toList());
                    row.enrichChairs(chairTmp);
                }
            }
            room.enrichRows(rowTmp);

            Optional<Location> locationOptional = locations.stream().filter(l ->Objects.equals(l.getId(), room.getLocationId())).findFirst();
            locationOptional.ifPresent(room::enrichLocation);
        }
        return rooms;
    }
}
