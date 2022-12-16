package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.FilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Film;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@Validated
public interface FilmResource {

    @PostMapping("/films")
    @PreAuthorize("hasPermission(null, 'film:create')")
    Response<Film> create(@RequestBody @Valid FilmCreateRequest request);

    @PostMapping("/films/{id}/update")
    @PreAuthorize("hasPermission(null, 'film:update')")
    Response<Film> update(@PathVariable("id") String id, @RequestBody @Valid FilmUpdateRequest request);

    @GetMapping("/films/{id}")
    @PreAuthorize("hasPermission(null, 'film:view')")
    Response<Film> getById(@PathVariable("id") String id);

    @GetMapping("/films")
    @PreAuthorize("hasPermission(null, 'film:view')")
    PagingResponse<Film> search(FilmSearchRequest request);

    @GetMapping("/films/auto-complete")
    @PreAuthorize("hasPermission(null, 'film:view')")
    PagingResponse<Film> autoComplete(FilmSearchRequest request);

}
