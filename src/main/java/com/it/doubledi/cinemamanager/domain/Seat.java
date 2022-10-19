package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Integer serialOfChair;
    private ChairType chairType;
    private Boolean deleted;
    private String rowId;
}
