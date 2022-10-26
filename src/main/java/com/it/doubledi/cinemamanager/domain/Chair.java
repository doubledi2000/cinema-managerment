package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
