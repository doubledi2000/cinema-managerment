package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceByTimeEntityRepository extends JpaRepository<PriceByTimeEntity, String> {
    @Query("FROM PriceByTimeEntity pbt WHERE pbt.deleted = false and pbt.priceConfigId in :ids")
    List<PriceByTimeEntity> findAllByPriceConfigIds(List<String> ids);
}
