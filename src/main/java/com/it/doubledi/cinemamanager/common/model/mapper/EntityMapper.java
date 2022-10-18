package com.it.doubledi.cinemamanager.common.model.mapper;

import java.util.List;

public interface EntityMapper <D, E>{
    D toDomain(E entity);

    E toEntity(D domain);

    List<D> toDomain(List<E> entities);

    List<E> toEntity(List<D> domains);

}
