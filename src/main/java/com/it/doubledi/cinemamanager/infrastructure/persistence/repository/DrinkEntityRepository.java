package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.DrinkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkEntityRepository extends JpaRepository<DrinkEntity, String> {

    @Query("from DrinkEntity d where d.deleted = false and d.locationId in :locationIds and d.status in :statuses")
    List<DrinkEntity> findAllByLocationIds(List<String> locationIds, List<DrinkStatus> drinkStatuses);
}
