package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager.common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Location extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private LocationStatus status;
    private Boolean deleted;

}
