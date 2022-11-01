package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Producer;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface ProducerResource {

    @PostMapping("/producers")
    Response<Producer> create(@RequestBody ProducerCreateRequest request);

    @PostMapping("/producers/{id}/update")
    Response<Producer> update(@PathVariable("id") String id, @RequestBody ProducerUpdateRequest request);

    @GetMapping("/producers/{id}")
    Response<Producer> getById(@PathVariable("id") String id);

    @GetMapping("/producers")
    PagingResponse<Producer> search(ProducerSearchRequest request);

    @GetMapping("/producers/auto-complete")
    PagingResponse<Producer> autoComplete(ProducerSearchRequest request);
}
