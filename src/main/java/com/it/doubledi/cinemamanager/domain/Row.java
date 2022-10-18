package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager.common.model.domain.AuditableDomain;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Row  extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Integer rowNumber;
    private Boolean deleted;
    private String roomId;

    private Room room;
    private List<Chair> chairs;
}
