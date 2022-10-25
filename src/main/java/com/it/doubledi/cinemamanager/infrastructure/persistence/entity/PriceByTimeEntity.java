package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "price_by_time")
@Data
public class PriceByTimeEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "start_at", nullable = false)
    private Integer startAt;

    @Column(name = "end_at", nullable = false)
    private Integer endAt;

    @Column(name = "config_price_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String configPriceId;

    @Column(name = "deleted")
    private Boolean deleted;

}
