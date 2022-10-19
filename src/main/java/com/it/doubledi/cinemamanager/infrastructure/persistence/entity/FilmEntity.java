package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;


import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "film", indexes = {
        @Index(name = "film_deleted_idx", columnList = "deleted"),
        @Index(name = "film_producer_id_idx", columnList = "producer_id"),
})
@Data
public class FilmEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "allowed_age_from")
    @Size(min = 0)
    private Integer allowedAgeFrom;

    @Column(name = "status", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    private FilmStatus status;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "ownership_date")
    private LocalDate ownershipDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "producer_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String producerId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        FilmEntity that = (FilmEntity) o;
        return id != null && Objects.equals(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
