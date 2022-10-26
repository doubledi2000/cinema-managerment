package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.FilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
public interface FilmResource {

    @PostMapping("/films")
    Response<Film> create(@RequestBody FilmCreateRequest request);

    @PostMapping("/films/{id}/update")
    Response<Film> update(@PathVariable("id") String id, @RequestBody @Valid FilmUpdateRequest request);

    @GetMapping("/films/{id}")
    Response<Film> getById(@PathVariable("id") String id);

    @GetMapping("/films")
    PagingResponse<PageDTO<Film>> search(FilmSearchRequest request);

    @GetMapping("/films/auto-complete")
    PagingResponse<PageDTO<Film>> autoComplete(FilmSearchRequest request);

}
