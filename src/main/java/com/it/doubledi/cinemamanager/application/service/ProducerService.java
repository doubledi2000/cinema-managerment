package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerUpdateRequest;
import com.it.doubledi.cinemamanager.domain.Producer;

public interface ProducerService {
    Producer create(ProducerCreateRequest request);

    Producer update(String id, ProducerUpdateRequest request);

    Producer getById(String id);

    PageDTO<Producer> search(ProducerSearchRequest request);

    PageDTO<Producer> autoComplete(ProducerSearchRequest request);

    void active(String id);

    void inactive(String id);
}
