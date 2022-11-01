package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoomUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private RoomStatus status;
    private Boolean deleted;
    private Integer maxRow;
    private Integer maxChairPerRow;
    private String locationId;

    private Location location;
    private List<Row> rows;

    public Room(RoomCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.maxRow = cmd.getMaxRow();
        this.maxChairPerRow = cmd.getMaxChairPerRow();
        this.locationId = cmd.getLocationId();
        this.status = RoomStatus.ACTIVE;
        this.deleted = Boolean.FALSE;
    }

    public void enrichRows(List<Row> rows) {
        if(CollectionUtils.isEmpty(rows)) {
            this.rows = new ArrayList<>();
        }else {
            this.rows = rows;
        }
    }

    public Room(Room room){
        this.id = IdUtils.nextId();
        this.code = room.getCode() + Constant.COPY_SUFFIX;
        this.name = room.getName() + Constant.COPY_SUFFIX;
        this.description = room.getDescription();
        this.description = room.getDescription();
        this.maxRow = room.getMaxRow();
        this.maxChairPerRow = room.getMaxChairPerRow();
        this.locationId = room.getLocationId();
        this.status = RoomStatus.ACTIVE;
        this.deleted = Boolean.FALSE;
    }

    public void delete(){

    }

    public void enrichLocation(Location location) {
        if(Objects.nonNull(location)) {
            this.location = location;
        }
    }

    public void update(RoomUpdateCmd cmd) {
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        for (Row row : this.getRows()) {
            for (Chair chair : row.getChairs()) {
                chair.delete();
            }
        }
    }

}
