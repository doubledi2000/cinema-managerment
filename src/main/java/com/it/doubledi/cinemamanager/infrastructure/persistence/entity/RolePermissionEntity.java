package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.enums.Property;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_permission", indexes = {
        @Index(name = "role_permission_role_id_idx", columnList = "role_id"),
        @Index(name = "role_permission_deleted_idx", columnList = "deleted")
}
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "role_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String roleId;

    @Column(name = "permission_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String permissionId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (id == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        RolePermissionEntity that = (RolePermissionEntity) o;
        return id != null & Objects.equals(that.getId(), id);
    }
}
