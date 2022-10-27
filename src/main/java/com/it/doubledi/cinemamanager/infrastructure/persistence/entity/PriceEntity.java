package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "chair_type", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private ChairType chairType;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "price_by_time_id")
    private String priceByTimeId;
}
