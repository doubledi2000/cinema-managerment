package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.TypeOfFilmService;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;
import com.it.doubledi.cinemamanager.presentation.web.TypeOfFilmResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TypeOfFilmResourceImpl implements TypeOfFilmResource {

    private final TypeOfFilmService typeOfFilmService;

    @Override
    public Response<TypeOfFilm> create(TypeOfFilmCreateRequest request) {
        return null;
    }

    @Override
    public Response<TypeOfFilm> update(String id, TypeOfFilmUpdateRequest request) {
        return null;
    }

    @Override
    public Response<TypeOfFilm> getById(String id) {
        return null;
    }

    @Override
    public PagingResponse<PageDTO<TypeOfFilm>> search(TypeOfFilmSearchRequest request) {
        return null;
    }

    @Override
    public PagingResponse<PageDTO<TypeOfFilm>> autoComplete(TypeOfFilmSearchRequest request) {
        return null;
    }
}
