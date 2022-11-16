package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.response.LocationPriceConfigResponse;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface PriceConfigResource {
    @GetMapping("/ticket-prices/{locationId}")
    Response<LocationPriceConfigResponse> getAllTicketPriceNotSpecial(@PathVariable("locationId") String locationId);
}
