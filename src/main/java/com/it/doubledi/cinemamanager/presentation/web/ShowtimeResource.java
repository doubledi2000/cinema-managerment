package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.domain.Showtime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api")
public interface ShowtimeResource {

    @PostMapping("/showtimes")
    Response<Showtime> createShowtime(@RequestBody @Valid ShowtimeCreateRequest request);
}
