package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
public interface ConfigPriceResource {
    @PostMapping("/ticket-price-configs")
    Response<List<PriceConfig>> setUpPrice(@RequestBody LocationPriceConfigRequest request);

    @PostMapping("/ticket-price-demo")
    Response<List<PriceConfig>> demo();

//    @GetMapping("/ticket-prices/{locationId}")
//    Response<List<PriceConfig>> getAllTicketPriceNotSpecial(@PathVariable("locationId") String locationId);

}
