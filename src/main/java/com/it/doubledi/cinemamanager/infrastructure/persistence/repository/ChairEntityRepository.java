package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ChairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChairEntityRepository extends JpaRepository<ChairEntity, String> {

    @Query("from ChairEntity c where c.deleted = false and c.rowId in :ids")
    List<ChairEntity> getAllChairByRowIds(@Param("ids") List<String> ids);
}
