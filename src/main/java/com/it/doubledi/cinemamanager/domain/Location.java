package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.LocationCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Location extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String address;
    private LocationStatus status;
    private Boolean deleted;
    private Long version;

    public Location(LocationCreateCmd cmd){
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.address = cmd.getAddress();
        this.status = LocationStatus.ACTIVE;
        this.deleted = Boolean.FALSE;
    }

    public void active(){
        this.status = LocationStatus.ACTIVE;
    }

    public void inactive(){
        this.status = LocationStatus.INACTIVE;
    }

}
