package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.enums.Gender;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.UserStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "username", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH, nullable = false)
    private String username;

    @Column(name = "password", length = ValidateConstraint.LENGTH.PASSWORD_MAX_LENGTH, nullable = false)
    private String password;

    @Column(name = "fullName", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, nullable = false)
    private String fullName;

    @Column(name = "email", length = ValidateConstraint.LENGTH.EMAIL_MAX_LENGTH)
    private String email;

    @Column(name = "phone_number", length = ValidateConstraint.LENGTH.PHONE_MAX_LENGTH)
    private String phoneNumber;

    @Column(name = "day_of_birth")
    private LocalDate dayOfBirth;

    @Column(name = "gender", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "employee_code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String employeeCode;

    @Column(name = "title", length = ValidateConstraint.LENGTH.TITLE_MAX_LENGTH)
    private String title;

    @Column(name = "department_name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String departmentName;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "status", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "user_level", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private UserLevel userLevel;

    @Column(name = "avatar_file_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String avatarFileId;
//
//    @Column(name = "version")
//    @Version
//    private Long version = 0L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
