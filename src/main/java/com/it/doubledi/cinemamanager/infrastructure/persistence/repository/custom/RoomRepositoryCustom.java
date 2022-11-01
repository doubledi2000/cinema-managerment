package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.RoomSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;

import java.util.List;

public interface RoomRepositoryCustom {

    List<RoomEntity> search(RoomSearchQuery searchQuery);

    Long count(RoomSearchQuery searchQuery);
}
