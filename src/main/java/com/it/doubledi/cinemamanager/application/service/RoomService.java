package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Room;

public interface RoomService {
    Room create(RoomCreateRequest request);

    Room update(String id, RoomUpdateRequest request);

    Room getById(String id);

    Boolean delete(String id);

    Room duplicateRoom(String id);

    PageDTO<Room> search(RoomSearchRequest request);

    PageDTO<Room> autoComplete(RoomSearchRequest request);
}
