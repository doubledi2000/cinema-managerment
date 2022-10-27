package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ShowtimeResponse {
    private String id;
    private LocalDate premiereDate;
    private Integer startAt;
    private Integer endAt;
    private String roomId;
    private String filmId;
    private Boolean deleted;
    private ShowtimeStatus status;
}
