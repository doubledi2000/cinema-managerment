package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.service.PriceConfigService;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import com.it.doubledi.cinemamanager.presentation.web.PriceConfigResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceConfigResourceImpl implements PriceConfigResource {

    private final PriceConfigService priceConfigService;

    public PriceConfigResourceImpl(PriceConfigService priceConfigService) {
        this.priceConfigService = priceConfigService;
    }

    @Override
    public Response<List<PriceConfig>> getAllTicketPriceNotSpecial(String locationId) {
        return Response.of(priceConfigService.getAllPriceConfigNotSpecial(locationId));
    }
}
