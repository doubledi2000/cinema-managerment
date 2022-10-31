package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.query.LocationSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;

import java.util.List;

public interface LocationRepositoryCustom {
    List<LocationEntity> search(LocationSearchQuery searchQuery);

    Long count(LocationSearchQuery searchQuery);
}
