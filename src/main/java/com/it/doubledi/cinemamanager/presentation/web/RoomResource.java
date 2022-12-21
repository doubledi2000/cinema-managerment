package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Room;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
public interface RoomResource {

    @PostMapping("/rooms")
    @PreAuthorize("hasPermission(null, 'room:create')")
    Response<Room> create(@RequestBody @Valid RoomCreateRequest request);

    @GetMapping("/rooms/{id}")
    @PreAuthorize("hasPermission(null, 'room:view')")
    Response<Room> getRoomById(@PathVariable("id") String id);

    @PostMapping("rooms/{id}/update")
    @PreAuthorize("hasPermission(null, 'room:update')")
    Response<Room> update(@PathVariable("id") String id, @RequestBody @Valid RoomUpdateRequest request);

    @PostMapping("/rooms/{id}/delete")
    @PreAuthorize("hasPermission(null, 'room:delete')")
    Response<Boolean> delete(@PathVariable("id") String id);

    @PostMapping("/rooms/{id}/active")
    @PreAuthorize("hasPermission(null, 'room:update')")
    Response<Boolean> active(@PathVariable("id") String id);

    @PostMapping("/rooms/{id}/inactive")
    @PreAuthorize("hasPermission(null, 'room:update')")
    Response<Boolean> inactive(@PathVariable("id") String id);

    @GetMapping("/rooms")
    @PreAuthorize("hasPermission(null, 'room:view')")
    PagingResponse<Room> search(RoomSearchRequest request);

    @GetMapping("/rooms/auto-complete")
    @PreAuthorize("hasPermission(null, 'room:view')")
    PagingResponse<Room> autoComplete(RoomSearchRequest request);

    @PostMapping("/rooms/{id}/duplicate")
    @PreAuthorize("hasPermission(null, 'room:create')")
    Response<Boolean> duplicate(@PathVariable("id") String id);
}
