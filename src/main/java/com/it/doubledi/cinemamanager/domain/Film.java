package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Film extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Integer allowedAgeFrom;
    private FilmStatus status;
    private String description;
    private LocalDate releaseDate;
    private LocalDate ownershipDate;
    private Integer duration;
    private String producerId;
    private Boolean deleted;
}
