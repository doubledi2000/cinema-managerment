package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ShowtimeCreateCmd {
    private String roomId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate premierDate;
    List<FilmScheduleCreateCmd> films;
}
