package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager.common.model.domain.AuditableDomain;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FilmType extends AuditableDomain {
    private String id;
    private String filmId;
    private String typeId;
    private Boolean deleted;
}
