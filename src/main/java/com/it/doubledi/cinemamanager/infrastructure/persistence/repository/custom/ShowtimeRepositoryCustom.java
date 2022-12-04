package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.ShowtimeConfigSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;

import java.util.List;

public interface ShowtimeRepositoryCustom {

    List<ShowtimeEntity> search(ShowtimeConfigSearchQuery searchQuery);

    Long count(ShowtimeConfigSearchQuery searchQuery);

}
