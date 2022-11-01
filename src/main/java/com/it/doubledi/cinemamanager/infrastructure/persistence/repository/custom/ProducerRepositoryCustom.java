package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom;

import com.it.doubledi.cinemamanager.domain.query.ProducerSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface ProducerRepositoryCustom {

    List<ProducerEntity> search(ProducerSearchQuery searchQuery);

    Long count(ProducerSearchQuery searchQuery);
}
