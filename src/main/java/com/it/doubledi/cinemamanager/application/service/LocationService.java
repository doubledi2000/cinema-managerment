package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.LocationCreateRequest;
import com.it.doubledi.cinemamanager.domain.Location;

public interface LocationService {
    Location create(LocationCreateRequest request);

    Location update(LocationCreateRequest request);

    Location getById(String id);
}
