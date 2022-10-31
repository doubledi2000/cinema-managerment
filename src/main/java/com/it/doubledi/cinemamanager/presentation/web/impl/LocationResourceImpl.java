package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.service.LocationService;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.presentation.web.LocationResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LocationResourceImpl implements LocationResource {
    private final LocationService locationService;


    @Override
    public Response<Location> create(LocationCreateRequest request) {
        return Response.of(locationService.create(request));
    }

    @Override
    public Response<Location> update(LocationCreateRequest request) {
        return null;
    }

    @Override
    public Response<Boolean> active(String id) {
        locationService.active(id);
        return Response.ok();
    }

    @Override
    public Response<Boolean> inactive(String id) {
        locationService.inactive(id);
        return Response.ok();
    }

    @Override
    public PagingResponse<Location> search(LocationSearchRequest request) {
        return PagingResponse.of(locationService.search(request));
    }

    @Override
    public PagingResponse<Location> autoComplete(LocationSearchRequest request) {
        return PagingResponse.of(locationService.autoComplete(request));
    }
}
