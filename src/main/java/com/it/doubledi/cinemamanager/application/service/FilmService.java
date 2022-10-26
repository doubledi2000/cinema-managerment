package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.FilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmSearchRequest;
import com.it.doubledi.cinemamanager.domain.Film;

public interface FilmService {
    Film create(FilmCreateRequest request);

    Film update(String id, FilmCreateRequest request);

    Film getById(String id);

    PageDTO<Film> search(FilmSearchRequest request);

    PageDTO<Film> autoComplete(FilmSearchRequest request);
}
