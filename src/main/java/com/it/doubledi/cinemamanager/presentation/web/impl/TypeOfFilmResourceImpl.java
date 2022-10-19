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
        return Response.of(typeOfFilmService.create(request));
    }

    @Override
    public Response<TypeOfFilm> update(String id, TypeOfFilmUpdateRequest request) {
        return Response.of(typeOfFilmService.update(id, request));
    }

    @Override
    public Response<TypeOfFilm> getById(String id) {
        return Response.of(typeOfFilmService.getById(id));
    }

    @Override
    public PagingResponse<TypeOfFilm> search(TypeOfFilmSearchRequest request) {
        return PagingResponse.of(typeOfFilmService.search(request));
    }

    @Override
    public PagingResponse<TypeOfFilm> autoComplete(TypeOfFilmSearchRequest request) {
        return PagingResponse.of(typeOfFilmService.autoComplete(request));
    }
}
