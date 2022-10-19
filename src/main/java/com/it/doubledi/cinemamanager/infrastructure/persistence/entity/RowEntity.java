package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "row", indexes = {
        @Index(name = "row_deleted_idx", columnList = "deleted"),
        @Index(name = "row_room_id_idx", columnList = "room_id")
})
@Data
public class RowEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "row_number")
    private Integer rowNumber;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "room_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String roomId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        RowEntity that = (RowEntity) o;
        return id != null && Objects.equals(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
