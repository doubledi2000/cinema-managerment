package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ChairUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Chair extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Integer serialOfChair;
    private ChairType chairType;
    private Boolean deleted;
    private String rowId;

    public void duplicate(String rowId, String code) {
        this.id = IdUtils.nextId();
        this.code = code;
        this.name += Constant.COPY_SUFFIX;
        this.rowId = rowId;
    }

    public void delete(){
        this.deleted = Boolean.TRUE;
    }

    public void undelete(){
        this.deleted = Boolean.FALSE;
    }

    public void update(ChairUpdateCmd cmd) {
        this.deleted = Boolean.FALSE;
        this.chairType = cmd.getChairType();
    }
}
