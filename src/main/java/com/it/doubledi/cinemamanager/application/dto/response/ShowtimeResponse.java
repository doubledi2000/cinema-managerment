package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.domain.Showtime;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class ShowtimeResponse {
    private LocalDate premiereDate;
    private String filmId;
    private List<Showtime> details;
    private Film film;
}
