package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "price_by_time")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PriceByTimeEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "start_at", nullable = false)
    private Integer startAt;

    @Column(name = "end_at", nullable = false)
    private Integer endAt;

    @Column(name = "config_price_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String priceConfigId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "priority")
    private Integer priority;

}
