package com.it.doubledi.cinemamanager.common.model.entity;

import com.it.doubledi.cinemamanager.common.model.validator.ValidateConstraint;
import lombok.Data;
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
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modify_by", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    protected String lastModifyBy;

    @LastModifiedDate
    @Column(name = "last_modify_at", nullable = false)
    protected Instant lastModifyAt = Instant.now();
}
