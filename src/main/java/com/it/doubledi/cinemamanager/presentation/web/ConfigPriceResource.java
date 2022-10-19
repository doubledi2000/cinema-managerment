package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.LocationPriceConfigRequest;
import com.it.doubledi.cinemamanager.domain.PriceConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@Valid
public interface ConfigPriceResource {
    @PostMapping("/ticket-price-configs")
    Response<List<PriceConfig>> setUpPrice(@RequestBody LocationPriceConfigRequest request);
}
