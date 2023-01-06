package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.FindByIdsRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TicketPriceConfigUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.response.LocationPriceConfigResponse;
import com.it.doubledi.cinemamanager.application.service.LocationService;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.presentation.web.LocationResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Response<Location> getById(String id) {
        return Response.of(locationService.getById(id));
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

    @Override
    public Response<LocationPriceConfigResponse> getTicketPriceConfigNormal(String id) {
        return Response.of(locationService.getAllPriceConfigNotSpecial(id));
    }

    @Override
    public Response<Boolean> updateTicketPrice(TicketPriceConfigUpdateRequest request) {
        locationService.updatePriceConfig(request);
        return Response.ok();
    }

    @Override
    public Response<List<Location>> findByIds(FindByIdsRequest request) {
        return Response.of(this.locationService.findByIds(request));
    }
}
