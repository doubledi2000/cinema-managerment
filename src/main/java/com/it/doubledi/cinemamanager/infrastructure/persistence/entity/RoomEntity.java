package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "room", indexes = {
        @Index(name = "room_deleted_ids", columnList = "deleted"),
        @Index(name = "room_location_id_idx", columnList = "location_id")
})
@Data
public class RoomEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "status", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    private RoomStatus status;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "max_row")
    @Size(min = 1)
    private Integer maxRow;

    @Column(name = "max_chair_per_row")
    @Size(min = 1)
    private Integer maxChairPerRow;

    @Column(name = "location_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String locationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        RoomEntity that = (RoomEntity) o;
        return id != null && Objects.equals(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
