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

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String actors;
    private String directors;
    private List<String> producerIds;
    private Boolean deleted;
    private Long version;

    private List<FilmType> filmTypes;
    private List<String> filmTypeIds;
    private List<FilmProducer> filmProducers;
    private List<Producer> producers;

    public Film(FilmCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.allowedAgeFrom = cmd.getAllowedAgeFrom();
        this.status = cmd.getStatus();
        this.description = cmd.getDescription();
        this.releaseDate = cmd.getReleaseDate();
        this.ownershipDate = cmd.getOwnershipDate();
        this.actors = cmd.getActors();
        this.directors = cmd.getDirectors();
        this.duration = cmd.getDuration();
        this.deleted = Boolean.FALSE;
    }

    public void enrichFilmType(List<FilmType> filmTypes) {
        if (CollectionUtils.isEmpty(filmTypes)) {
            this.filmTypes = new ArrayList<>();
        } else {
            this.filmTypes = filmTypes;
            this.filmTypeIds = filmTypes.stream().map(FilmType::getTypeId).collect(Collectors.toList());
        }
    }

    public void enrichProducer(List<FilmProducer> filmProducers) {
        if (CollectionUtils.isEmpty(filmProducers)) {
            this.filmProducers = new ArrayList<>();
        } else {
            this.filmProducers = filmProducers;
            this.producerIds = filmProducers.stream().map(FilmProducer::getProducerId).collect(Collectors.toList());
        }
    }
}

