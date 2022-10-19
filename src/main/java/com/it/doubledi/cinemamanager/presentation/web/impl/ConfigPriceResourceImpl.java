package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.application.service.PriceConfigService;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.presentation.web.ConfigPriceResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ConfigPriceResourceImpl implements ConfigPriceResource {

    private final PriceConfigService configPriceService;

    @Override
    public Response<List<PriceConfig>> setUpPrice(LocationPriceConfigRequest request) {
        return Response.of(configPriceService.setUp(request));
    }
}
