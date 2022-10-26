package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.domain.Showtime;

public interface ShowtimeService {
    Showtime create(ShowtimeCreateRequest request);

    Showtime getById(String id);

    PageDTO<Showtime> search(ShowtimeSearchRequest request);

    PageDTO<Showtime> autoComplete(ShowtimeSearchRequest request);
}
