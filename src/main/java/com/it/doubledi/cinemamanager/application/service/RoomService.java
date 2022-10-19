package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Room;

public interface RoomService {
    Room create(RoomCreateRequest request);

    Room update(String id, RoomUpdateRequest request);

    Room getById(String id);

    Boolean delete(String id);

    Room duplicateRoom(String id, RoomCreateRequest request);

}
