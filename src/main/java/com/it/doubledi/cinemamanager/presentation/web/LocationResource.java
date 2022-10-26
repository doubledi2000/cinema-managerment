package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.domain.Location;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface LocationResource {

    @PostMapping("/locations")
    Response<Location> create(@RequestBody LocationCreateRequest request);

    @PostMapping("/locations/{id}/update")
    Response<Location> update(LocationCreateRequest request);

    @PostMapping("/locations/{id}/active")
    Response<Boolean> active();

    @PostMapping("/locations/{id}/inactive")
    Response<Boolean> inactive();

    @GetMapping("/locations")
    PagingResponse<PageDTO<Location>> search();

    @GetMapping("/locations/auto-complete")
    PagingResponse<PageDTO<Location>> autoComplete();
}
