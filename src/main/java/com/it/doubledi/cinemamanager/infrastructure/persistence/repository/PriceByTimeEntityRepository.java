package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.domain.PriceByTime;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceByTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceByTimeEntityRepository extends JpaRepository<PriceByTimeEntity, String> {
    @Query("FROM PriceByTimeEntity pbt WHERE pbt.deleted = false and pbt.priceConfigId in :ids")
    List<PriceByTimeEntity> findAllByPriceConfigIds(List<String> ids);

    @Query("Select pbt from PriceByTimeEntity pbt inner join PriceConfigEntity pc on pbt.priceConfigId = pc.id " +
            " where pc.locationId = :locationId and pc.dayOfWeek = :dayOfWeek and pbt.deleted = false and pbt.startAt <= :timeAt and pbt.endAt >= :timeAt")
    Optional<PriceByTimeEntity> getPriceByTimeBySpecificTime(String locationId, int dayOfWeek , int timeAt);
}
