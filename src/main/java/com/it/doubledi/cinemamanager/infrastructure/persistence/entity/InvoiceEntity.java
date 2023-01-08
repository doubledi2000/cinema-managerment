package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "invoice")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "user_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String userId;

    @Column(name = "customer_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String customerId;

    @Column(name = "payment_time")
    private Instant paymentTime;

    @Column(name = "total")
    private Double total;

    @Column(name = "location_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String locationId;

    @Column(name = "deleted")
    private Boolean deleted;

}
