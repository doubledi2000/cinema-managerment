package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Showtime extends AuditableDomain {
    private String id;
    private LocalDate premiereDate;
    private Integer startAt;
    private Integer endAt;
    private String roomId;
    private String filmId;
    private Boolean deleted;
    private ShowtimeStatus status;
    private Boolean autoGenerateTicket;
}
