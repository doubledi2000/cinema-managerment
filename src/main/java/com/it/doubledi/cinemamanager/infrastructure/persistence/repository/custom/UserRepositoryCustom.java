package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.UserSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserEntity> search(UserSearchQuery searchQuery);

    Long count(UserSearchQuery searchQuery);
}
