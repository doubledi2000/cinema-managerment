package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ShowtimeCreateCmd {
    private String filmId;
    private String roomId;
    private LocalDate premiereDate;
    private Integer startAt;
    private Boolean autoGenerateTicket;
}
