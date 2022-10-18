package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager.common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager.common.model.validator.ValidateConstraint;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "producer", indexes = {
        @Index(name = "producer_deleted_idx", columnList = "deleted")
})
@Data
public class ProducerEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "deleted")
    private String deleted;

    @Column(name = "representative", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String representative;

    @Column(name = "nationally", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String nationally;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        ProducerEntity that = (ProducerEntity) o;
        return id != null && Objects.equals(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
