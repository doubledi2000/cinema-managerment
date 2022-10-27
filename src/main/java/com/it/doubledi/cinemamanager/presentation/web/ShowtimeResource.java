package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.domain.Showtime;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
public interface ShowtimeResource {

    @PostMapping("/showtimes")
    Response<Showtime> createShowtime(@RequestBody @Valid ShowtimeCreateRequest request);

    @PostMapping("/showtimes/{id}/generate-tickets")
    Response<Boolean> generateTicket(@PathVariable("id") String id);

    @GetMapping("/showtimes/{id}")
    Response<Showtime> findById(@PathVariable("id") String id);
}
