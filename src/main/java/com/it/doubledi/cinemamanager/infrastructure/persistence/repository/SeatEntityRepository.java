package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatEntityRepository extends JpaRepository<SeatEntity, String> {
}
