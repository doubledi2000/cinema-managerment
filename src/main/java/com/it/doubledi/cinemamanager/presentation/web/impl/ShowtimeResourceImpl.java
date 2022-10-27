package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.service.ShowtimeService;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.presentation.web.ShowtimeResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ShowtimeResourceImpl implements ShowtimeResource {

    private final ShowtimeService showtimeService;

    @Override
    public Response<Showtime> createShowtime(ShowtimeCreateRequest request) {
        return Response.of(showtimeService.create(request));
    }

    @Override
    public Response<Boolean> generateTicket(String id) {
        showtimeService.generateTicket(id);
        return Response.ok();
    }

    @Override
    public Response<Showtime> findById(String id) {
        return Response.of(showtimeService.getById(id));
    }
}
