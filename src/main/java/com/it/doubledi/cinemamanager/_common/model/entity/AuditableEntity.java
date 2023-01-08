package com.it.doubledi.cinemamanager._common.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected Instant createdAt = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    protected Instant lastModifiedAt = Instant.now();
}
