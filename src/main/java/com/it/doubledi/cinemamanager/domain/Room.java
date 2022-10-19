package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.RoomCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private Boolean defaultSetting;

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
        this.defaultSetting = cmd.getDefaultSetting();
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

    public void delete(){

    }
}
