package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager.common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
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
}
