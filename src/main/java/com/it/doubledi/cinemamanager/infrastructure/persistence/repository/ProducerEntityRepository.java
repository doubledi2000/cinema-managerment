package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerEntityRepository extends JpaRepository<ProducerEntity, String> {
}
