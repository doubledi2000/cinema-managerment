package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager.common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager.common.model.validator.ValidateConstraint;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "film_type", indexes = {
        @Index(name = "film_type_deleted_idx", columnList = "deleted"),
        @Index(name = "film_type_film_id_idx", columnList = "film_id"),
        @Index(name = "film_type_type_id_idx", columnList = "type_id")
})
@Data
public class FilmTypeEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "film_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String filmId;

    @Column(name = "type_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String typeId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        FilmTypeEntity that = (FilmTypeEntity) o;
        return id != null && Objects.equals(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
