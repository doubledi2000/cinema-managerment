package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerUpdateRequest;
import com.it.doubledi.cinemamanager.application.service.ProducerService;
import com.it.doubledi.cinemamanager.domain.Producer;
import com.it.doubledi.cinemamanager.presentation.web.ProducerResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProducerResourceImpl implements ProducerResource {

    private final ProducerService producerService;

    @Override
    public Response<Producer> create(ProducerCreateRequest request) {
        return Response.of(producerService.create(request));
    }

    @Override
    public Response<Producer> update(String id, ProducerUpdateRequest request) {
        return Response.of(producerService.update(id, request));
    }

    @Override
    public Response<Producer> getById(String id) {
        return Response.of(producerService.getById(id));
    }

    @Override
    public PagingResponse<Producer> search(ProducerSearchRequest request) {
        return PagingResponse.of(producerService.search(request));
    }

    @Override
    public PagingResponse<Producer> autoComplete(ProducerSearchRequest request) {
        return PagingResponse.of(producerService.autoComplete(request));
    }
}
