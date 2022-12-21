package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Drink;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface DrinkResource {

    @PostMapping("/drinks")
    @PreAuthorize("hasPermission(null, 'drink:create')")
    Response<Drink> create(@RequestBody DrinkCreateRequest request);

    @PostMapping("/drinks/{id}/update")
    @PreAuthorize("hasPermission(null, 'drink:update')")
    Response<Drink> update(@PathVariable("id") String id, @RequestBody DrinkUpdateRequest request);

    @GetMapping("/drinks/{id}")
    @PreAuthorize("hasPermission(null, 'drink:view')")
    Response<Drink> getById(@PathVariable("id") String id);

    @PostMapping("/drinks/{id}/active")
    @PreAuthorize("hasPermission(null, 'drink:update')")
    Response<Boolean> active(@PathVariable("id") String id);

    @PostMapping("/drinks/{id}/inactive")
    @PreAuthorize("hasPermission(null, 'drink:update')")
    Response<Boolean> inactive(@PathVariable("id") String id);

    @GetMapping("/drinks")
    @PreAuthorize("hasPermission(null, 'drink:view')")
    PagingResponse<Drink> search(DrinkSearchRequest request);
}
