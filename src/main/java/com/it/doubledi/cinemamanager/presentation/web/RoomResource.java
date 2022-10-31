package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager.application.dto.request.RoomCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchAutoCompleteRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RoomUpdateRequest;
import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.domain.Room;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface RoomResource {

    @PostMapping("/rooms")
    Response<Room> create(@RequestBody @Valid RoomCreateRequest request);

    @GetMapping("/rooms/{id}")
    Response<Room> getRoomById(@PathVariable("id") String id);

    @PostMapping("rooms/{id}/update")
    Response<Room> update(@PathVariable("id") String id, @RequestBody @Valid RoomUpdateRequest request);

    @PostMapping("/rooms/{id}/delete")
    Response<Boolean> delete(@PathVariable("id") String id);

    @PostMapping("/rooms/{id}/active")
    Response<Boolean> active(@PathVariable("id") String id);

    @PostMapping("/rooms/{id}/inactive")
    Response<Boolean> inactive(@PathVariable("id") String id);

    @GetMapping("/rooms")
    PagingResponse<Room> search(RoomSearchRequest request);

    @GetMapping("/rooms/auto-complete")
    PagingResponse<PageDTO<Room>> autoComplete(RoomSearchAutoCompleteRequest request);

    @PostMapping("/rooms/{id}/duplicate")
    Response<Boolean> duplicate(@PathVariable("id") String id);
}
