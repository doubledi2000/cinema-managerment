package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.RoomService;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.presentation.web.RoomResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoomResourceImpl implements RoomResource {

    private final RoomService roomService;

    @Override
    public Response<Room> create(RoomCreateRequest request) {
        return Response.of(roomService.create(request));
    }

    @Override
    public Response<Room> getRoomById(String id) {
        return Response.of(roomService.getById(id));
    }

    @Override
    public Response<Room> update(String id, RoomUpdateRequest request) {
        return Response.of(roomService.update(id, request));
    }

    @Override
    public Response<Boolean> delete(String id) {
        return Response.of(roomService.delete(id));
    }

    @Override
    public Response<Boolean> active(String id) {
        return null;
    }

    @Override
    public Response<Boolean> inactive(String id) {
        return null;
    }

    @Override
    public PagingResponse<Room> search(RoomSearchRequest request) {
        return PagingResponse.of(roomService.search(request));
    }

    @Override
    public PagingResponse<Room> autoComplete(RoomSearchRequest request) {
        return PagingResponse.of(roomService.autoComplete(request));
    }

    @Override
    public Response<Boolean> duplicate(String id) {
        return Response.of(roomService.duplicateRoom(id));
    }


}
