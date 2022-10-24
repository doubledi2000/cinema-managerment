package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;

@Entity
@Table(name = "price_config")
public class PriceConfigEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PriceConfigType type;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_at")
    private Integer startAt;

    @Column(name = "end_at")
    private Integer endAt;

    @Column(name = "price")
    private Float price;

    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

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

    private Boolean deleted;
}
