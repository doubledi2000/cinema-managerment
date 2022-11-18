package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmUpdateRequest;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@Valid
@CrossOrigin("*")
public interface TypeOfFilmResource {
    @PostMapping("/type-of-films")
    Response<TypeOfFilm> create(@RequestBody @Valid TypeOfFilmCreateRequest request);

    @PostMapping("/type-of-films/{id}/update")
    Response<TypeOfFilm> update(@PathVariable("id") String id,@RequestBody @Valid TypeOfFilmUpdateRequest request);

    @GetMapping("/type-of-films/{id}")
    Response<TypeOfFilm> getById(@PathVariable("id") String id);

    @GetMapping("/type-of-films")
    @PreAuthorize("hasPermission(null,'usekr:createf')")
    PagingResponse<TypeOfFilm> search(TypeOfFilmSearchRequest request);

    @GetMapping("/type-of-films/auto-complete")
    PagingResponse<TypeOfFilm>autoComplete(TypeOfFilmSearchRequest request);

}
