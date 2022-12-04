package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeConfigSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.response.ShowtimeResponse;
import com.it.doubledi.cinemamanager.domain.Showtime;

import java.util.List;

public interface ShowtimeService {
    Showtime create(ShowtimeCreateRequest request);

    Showtime getById(String id);

    List<ShowtimeResponse> search(ShowtimeSearchRequest request);

    PageDTO<Showtime> autoComplete(ShowtimeSearchRequest request);

    void generateTicket(String id);

    List<Showtime> createMulti(ShowtimeCreateRequest request);

    PageDTO<Showtime> getShowtimeConfig(ShowtimeConfigSearchRequest request);
}
