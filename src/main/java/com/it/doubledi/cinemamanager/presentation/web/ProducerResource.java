package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Producer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface ProducerResource {

    @PostMapping("/producers")
    @PreAuthorize("hasPermission(null,'producer:create')")
    Response<Producer> create(@RequestBody ProducerCreateRequest request);

    @PostMapping("/producers/{id}/update")
    @PreAuthorize("hasPermission(null, 'producer:update')")
    Response<Producer> update(@PathVariable("id") String id, @RequestBody ProducerUpdateRequest request);

    @GetMapping("/producers/{id}")
    @PreAuthorize("hasPermission(null, 'producer:view')")
    Response<Producer> getById(@PathVariable("id") String id);

    @GetMapping("/producers")
    @PreAuthorize("hasPermission(null, 'producer:view')")
    PagingResponse<Producer> search(ProducerSearchRequest request);

    @GetMapping("/producers/auto-complete")
    @PreAuthorize("hasPermission(null, 'producer:view')")
    PagingResponse<Producer> autoComplete(ProducerSearchRequest request);

    @PostMapping("/producers/{id}/active")
    @PreAuthorize("hasPermission(null, 'producer:update')")
    Response<Boolean> active(@PathVariable("id") String id);

    @PostMapping("/producers/{id}/inactive")
    @PreAuthorize("hasPermission(null, 'producer:update')")
    Response<Boolean> inactive(@PathVariable("id") String id);
}
