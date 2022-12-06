package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.DrinkService;
import com.it.doubledi.cinemamanager.domain.Drink;
import com.it.doubledi.cinemamanager.presentation.web.DrinkResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DrinkResourceImpl implements DrinkResource {

    private final DrinkService drinkService;

    @Override
    public Response<Drink> create(DrinkCreateRequest request) {
        return Response.of(drinkService.create(request));
    }

    @Override
    public Response<Drink> update(String id, DrinkUpdateRequest request) {
        return Response.of(drinkService.update(id, request));
    }

    @Override
    public Response<Drink> getById(String id) {
        return Response.of(drinkService.getById(id));
    }

    @Override
    public Response<Boolean> active(String id) {
        drinkService.active(id);
        return Response.ok();
    }

    @Override
    public Response<Boolean> inactive(String id) {
        drinkService.inactive(id);
        return Response.ok();
    }

    @Override
    public PagingResponse<Drink> search(DrinkSearchRequest request) {
        return PagingResponse.of(this.drinkService.search(request));
    }
}
