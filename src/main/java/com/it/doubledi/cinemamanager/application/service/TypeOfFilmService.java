package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmUpdateRequest;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;

public interface TypeOfFilmService {
    TypeOfFilm create(TypeOfFilmCreateRequest request);

    TypeOfFilm update(String id, TypeOfFilmUpdateRequest request);

    TypeOfFilm getById(String id);

    PageDTO<TypeOfFilm> search(TypeOfFilmSearchRequest request);

    PageDTO<TypeOfFilm> autoComplete(TypeOfFilmSearchRequest request);
}
