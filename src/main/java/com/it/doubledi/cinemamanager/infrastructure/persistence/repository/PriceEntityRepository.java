package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceEntityRepository extends JpaRepository<PriceEntity, String> {

    @Query("from PriceEntity p where p.deleted = false and p.priceByTimeId in :ids order by p.priority")
    List<PriceEntity> getAllByPriceByTimeIds(List<String> ids);

}
