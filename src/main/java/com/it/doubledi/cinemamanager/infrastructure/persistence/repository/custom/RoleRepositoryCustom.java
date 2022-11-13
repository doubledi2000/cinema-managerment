package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.RoleSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;

import java.util.List;

public interface RoleRepositoryCustom {

    List<RoleEntity> search(RoleSearchQuery searchQuery);

    Long count(RoleSearchQuery searchQuery);
}
