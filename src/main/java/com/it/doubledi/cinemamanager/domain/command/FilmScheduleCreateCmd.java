package com.it.doubledi.cinemamanager.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmScheduleCreateCmd {
    private Integer startAt;
    private String filmId;
    private Boolean autoGenerateTicket;
}
