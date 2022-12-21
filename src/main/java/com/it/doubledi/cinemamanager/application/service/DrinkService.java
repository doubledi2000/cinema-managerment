package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Drink;

public interface DrinkService {
    Drink create(DrinkCreateRequest request);

    Drink update(String id, DrinkUpdateRequest request);

    Drink getById(String id);

    void active(String id);

    void inactive(String id);

    PageDTO<Drink> search(DrinkSearchRequest request);

    PageDTO<Drink> autoComplete(DrinkSearchRequest request);
}
