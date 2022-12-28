package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.DrinkStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "drinks")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrinkEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DrinkStatus status;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "file_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String fileId;

//    @Column(name = "version" )
//    @Version
//    private Long version = 0L;

}
