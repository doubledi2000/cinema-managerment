package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.FilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.FilmService;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.presentation.web.FilmResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class FilmResourceImpl implements FilmResource {

    private final FilmService filmService;

    @Override
    public Response<Film> create(FilmCreateRequest request) {
        return Response.of(filmService.create(request));
    }

    @Override
    public Response<Film> update(String id, FilmUpdateRequest request) {
        return null;
    }

    @Override
    public Response<Film> getById(String id) {
        return null;
    }

    @Override
    public PagingResponse<PageDTO<Film>> search(FilmSearchRequest request) {
        return null;
    }

    @Override
    public PagingResponse<PageDTO<Film>> autoComplete(FilmSearchRequest request) {
        return null;
    }
}
