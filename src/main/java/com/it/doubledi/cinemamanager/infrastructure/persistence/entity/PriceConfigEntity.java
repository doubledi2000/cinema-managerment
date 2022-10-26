package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.SpecialBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "price_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceConfigEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PriceConfigType type;

    @Column(name = "day_of_week")
//    @Enumerated(EnumType.STRING)
    private int dayOfWeek;

    @Column(name = "drink_id")
    private String drinkId;

    @Column(name = "special")
    private Boolean special;

    @Column(name = "special_by", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private SpecialBy specialBy;

    @Column(name = "status", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private PriceConfigStatus status;

    @Column(name = "location_id")
    private String locationId;

    private Boolean deleted;
}
