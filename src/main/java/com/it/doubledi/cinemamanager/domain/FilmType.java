package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmType extends AuditableDomain {
    private String id;
    private String filmId;
    private String typeId;
    private String typeCode;
    private String typeName;
    private String typeDescription;
    private Boolean deleted;

    public FilmType(String filmId, String typeId){
        this.id = IdUtils.nextId();
        this.filmId = filmId;
        this.typeId = typeId;
        this.deleted = Boolean.FALSE;
    }


    public void delete(){
        this.deleted = Boolean.TRUE;
    }

    public void undelete(){
        this.deleted = Boolean.FALSE;
    }

    public void enrichType(TypeOfFilm typeOfFilm) {
        this.typeCode = typeOfFilm.getCode();
        this.typeName = typeOfFilm.getName();
        this.typeDescription = typeOfFilm.getDescription();
    }
}
