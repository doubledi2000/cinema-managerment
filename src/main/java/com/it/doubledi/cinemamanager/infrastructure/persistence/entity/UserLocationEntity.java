package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user_location")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "building_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String buildingId;

    @Column(name = "user_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String userId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        UserLocationEntity that = (UserLocationEntity) o;
        return o != null && Objects.equals(that.getId(), id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
