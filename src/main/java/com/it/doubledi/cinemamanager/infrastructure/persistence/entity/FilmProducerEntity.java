package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "film_producer", indexes = {
        @Index(name = "film_producer_deleted_idx", columnList = "deleted")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmProducerEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "film_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String filmId;

    @Column(name = "producer_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String producerId;

    @Column(name = "deleted")
    private Boolean deleted;
}
