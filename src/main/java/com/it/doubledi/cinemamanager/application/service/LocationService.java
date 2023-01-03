package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.FindByIdsRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.LocationSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TicketPriceConfigUpdateRequest;
import com.it.doubledi.cinemamanager.application.dto.response.LocationPriceConfigResponse;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.query.LocationSearchQuery;

import java.util.List;

public interface LocationService {
    Location create(LocationCreateRequest request);

    Location update(String id, LocationCreateRequest request);

    Location getById(String id);

    void active(String id);

    void inactive(String id);

    PageDTO<Location> search(LocationSearchRequest request);

    PageDTO<Location> autoComplete(LocationSearchRequest request);

    LocationPriceConfigResponse getAllPriceConfigNotSpecial(String locationId);

    void updatePriceConfig(TicketPriceConfigUpdateRequest request);

    List<Location> findByIds(FindByIdsRequest request);
}
