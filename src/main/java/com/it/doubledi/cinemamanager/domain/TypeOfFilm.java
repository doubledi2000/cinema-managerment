package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.TypeOfFilmCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.TypeOfFilmUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TypeOfFilmStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//@SuperBuilder
public class TypeOfFilm extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private TypeOfFilmStatus status;
    private Boolean deleted;

    public TypeOfFilm(TypeOfFilmCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.status = TypeOfFilmStatus.ACTIVE;
        this.deleted = Boolean.FALSE;
    }

    public void update(TypeOfFilmUpdateCmd cmd) {
        this.name = cmd.getName();
        this.description = cmd.getDescription();
    }
}
