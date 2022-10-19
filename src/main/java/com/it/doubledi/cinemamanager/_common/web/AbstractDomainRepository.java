package com.it.doubledi.cinemamanager._common.web;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public abstract class AbstractDomainRepository<D, E, I> implements DomainRepository<D, I> {
    protected final JpaRepository<E, I> jpaRepository;
    protected final EntityMapper<D, E> entityMapper;

    public AbstractDomainRepository(JpaRepository<E, I> jpaRepository, EntityMapper<D, E> entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Optional<D> findById(I id) {
        return this.jpaRepository.findById(id).map(this.entityMapper::toDomain).map(this::enrich);
    }

    @Override
    public List<D> findAllByIds(List<I> ids) {
        return this.enrichList(this.jpaRepository.findAllById(ids).stream().map(this.entityMapper::toDomain).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public D save(D domain) {
        E entity = this.entityMapper.toEntity(domain);
        this.jpaRepository.save(entity);
        return domain;
    }

    @Override
    @Transactional
    public List<D> saveALl(List<D> domains) {
        List<E> entities = this.entityMapper.toEntity(domains);
        this.jpaRepository.saveAll(entities);
        return domains;
    }

    protected D enrich(D d) {
        List<D> ds = List.of(d);
        return this.enrichList(ds).get(0);
    }

    protected List<D> enrichList(List<D> ds) {
        return ds;
    }
}

