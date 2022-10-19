package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;

import javax.persistence.*;

@Entity
@Table(name = "seat", indexes = {
        @Index(name = "seat_deleted_idx", columnList = "deleted"),
        @Index(name = "seat_row_id_idx", columnList = "row_id")
})
public class SeatEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "serialOfChair")
    private Integer serialOfChair;

    @Column(name = "chair_type", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    private ChairType chairType;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "row_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String rowId;
}
