package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TicketPriceConfigUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.response.LocationPriceConfigResponse;
import com.it.doubledi.cinemamanager.domain.Location;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface LocationResource {

    @PostMapping("/locations")
    @PreAuthorize("hasPermission(null, 'location:create')")
    Response<Location> create(@RequestBody LocationCreateRequest request);

    @PostMapping("/locations/{id}/update")
    @PreAuthorize("hasPermission(null, 'location:update')")
    Response<Location> update(LocationCreateRequest request);

    @PostMapping("/locations/{id}/active")
    @PreAuthorize("hasPermission(null, 'location:update')")
    Response<Boolean> active(@PathVariable("id") String id);

    @PostMapping("/locations/{id}/inactive")
    @PreAuthorize("hasPermission(null, 'location:update')")
    Response<Boolean> inactive(@PathVariable("id") String id);

    @GetMapping("/locations")
    @PreAuthorize("hasPermission(null, 'location:view')")
    PagingResponse<Location> search(LocationSearchRequest request);

    @GetMapping("/locations/auto-complete")
    @PreAuthorize("hasPermission(null, 'location:view')")
    PagingResponse<Location> autoComplete(LocationSearchRequest request);

    @GetMapping("/locations/{id}/ticket-price-normal")
    @PreAuthorize("hasPermission(null, 'price:view')")
    Response<LocationPriceConfigResponse> getTicketPriceConfigNormal(@PathVariable("id") String id);

    @PostMapping("/locations/update/ticket-price-normal")
    @PreAuthorize("hasPermission(null, 'price:update')")
    Response<Boolean> updateTicketPrice(@RequestBody TicketPriceConfigUpdateRequest request);
}
