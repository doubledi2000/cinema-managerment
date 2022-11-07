package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilmCreateCmd {
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
    private List<String> producerId;
    private List<String> filmTypeIds;
}
