package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleLevel;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role", indexes = {
        @Index(name = "role_code_idx", columnList = "code"),
        @Index(name = "role_deleted_idx", columnList = "deleted")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "is_root", nullable = false)
    private Boolean isRoot;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "status", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private RoleStatus status;

    @Column(name = "role_level", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private RoleLevel roleLevel;

    @Column(name = "version")
    @Version
    private Long version = 0L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        RoleEntity that = (RoleEntity) o;
        return id != null && Objects.equals(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
