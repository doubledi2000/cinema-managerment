package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TypeOfFilmStatus;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfFilm extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private TypeOfFilmStatus status;
    private Boolean deleted;
}
