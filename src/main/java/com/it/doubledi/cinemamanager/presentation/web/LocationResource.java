package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.domain.Location;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface LocationResource {

    @PostMapping("/locations")
    Response<Location> create(@RequestBody LocationCreateRequest request);

    @PostMapping("/locations/{id}/update")
    Response<Location> update(LocationCreateRequest request);

    @PostMapping("/locations/{id}/active")
    Response<Boolean> active(@PathVariable("id") String id);

    @PostMapping("/locations/{id}/inactive")
    Response<Boolean> inactive(@PathVariable("id") String id);

    @GetMapping("/locations")
    PagingResponse<Location> search(LocationSearchRequest request);

    @GetMapping("/locations/auto-complete")
    PagingResponse<Location> autoComplete(LocationSearchRequest request);
}
