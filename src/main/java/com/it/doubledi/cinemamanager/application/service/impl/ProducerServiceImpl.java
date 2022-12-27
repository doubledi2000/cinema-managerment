package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.mapper.util.PageableMapperUtil;
import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ProducerUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.ProducerService;
import com.it.doubledi.cinemamanager.domain.Producer;
import com.it.doubledi.cinemamanager.domain.command.ProducerCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ProducerUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.ProducerSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.ProducerRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ProducerEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ProducerEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final AutoMapper autoMapper;
    private final AutoMapperQuery autoMapperQuery;
    private final ProducerRepository producerRepository;
    private final ProducerEntityRepository producerEntityRepository;
    private final ProducerEntityMapper producerEntityMapper;

    @Override
    public Producer create(ProducerCreateRequest request) {
        ProducerCreateCmd cmd = this.autoMapper.from(request);
        Producer producer = new Producer(cmd);
        this.producerRepository.save(producer);
        return producer;
    }

    @Override
    public Producer update(String id, ProducerUpdateRequest request) {
        Producer producer = this.producerRepository.getById(id);
        ProducerUpdateCmd cmd = this.autoMapper.from(request);
        producer.update(cmd);
        this.producerRepository.save(producer);
        return producer;
    }

    @Override
    public Producer getById(String id) {
        return producerRepository.getById(id);
    }

    @Override
    public PageDTO<Producer> search(ProducerSearchRequest request) {
        ProducerSearchQuery query = this.autoMapperQuery.toQuery(request);
        Long count = this.producerEntityRepository.count(query);
        if (count == 0) {
            return PageDTO.empty();
        }
        List<ProducerEntity> producerEntities = this.producerEntityRepository.search(query);
        List<Producer> producers = this.producerEntityMapper.toDomain(producerEntities);

        return new PageDTO<>(producers, query.getPageIndex(), query.getPageSize(), count);
    }

    @Override
    public PageDTO<Producer> autoComplete(ProducerSearchRequest request) {
        Pageable pageable = PageableMapperUtil.toPageable(request);
        Page<ProducerEntity> producerEntityPage = this.producerEntityRepository.autoComplete(SqlUtils.encodeKeyword(request.getKeyword()), pageable);
        List<ProducerEntity> producerEntities = producerEntityPage.getContent();
        return new PageDTO<>(this.producerEntityMapper.toDomain(producerEntities),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                producerEntityPage.getTotalElements());
    }

    @Override
    public void active(String id) {
        Producer producer = this.producerRepository.getById(id);
        producer.active();
        this.producerRepository.save(producer);
    }

    @Override
    public void inactive(String id) {
        Producer producer = this.producerRepository.getById(id);
        producer.inactive();
        this.producerRepository.save(producer);
    }
}
