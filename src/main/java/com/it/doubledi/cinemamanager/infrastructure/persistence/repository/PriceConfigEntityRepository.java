package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceConfigEntityRepository extends JpaRepository<PriceConfigEntity, String> {
    @Query("From PriceConfigEntity pc where pc.deleted = false")
    List<PriceConfigEntity> getAllPriceConfigs();

    @Query("from PriceConfigEntity pc where pc.deleted = false and pc.locationId = :locationId")
    List<PriceConfigEntity> getAllByLocationId(@Param("locationId") String locationId);
}
