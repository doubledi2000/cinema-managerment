package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.FilmCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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

    private List<FilmType> filmTypes;
    private List<TypeOfFilm> typeOfFilms;

    public Film(FilmCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.allowedAgeFrom = cmd.getAllowedAgeFrom();
        this.status = cmd.getStatus();
        this.description = cmd.getDescription();
        this.releaseDate = cmd.getReleaseDate();
        this.ownershipDate = cmd.getOwnershipDate();
        this.duration = cmd.getDuration();
        this.producerId = cmd.getProducerId();
        this.deleted = Boolean.FALSE;
    }

    public void enrichFilmType(List<FilmType> filmTypes) {
        if (CollectionUtils.isEmpty(filmTypes)) {
            this.filmTypes = new ArrayList<>();
        } else {
            this.filmTypes = filmTypes;
        }
    }

    public void enrichTypeOfFilm(List<TypeOfFilm> typeOfFilms) {
        if(CollectionUtils.isEmpty(typeOfFilms)) {
            this.typeOfFilms = new ArrayList<>();
        }else {
            this.typeOfFilms = typeOfFilms;
        }
    }
}

