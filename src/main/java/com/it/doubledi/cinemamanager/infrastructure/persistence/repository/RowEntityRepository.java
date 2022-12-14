package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowEntityRepository extends JpaRepository<RowEntity, String> {

    @Query("from RowEntity r where r.deleted = false and r.roomId = :id")
    List<RowEntity> findRowByRoomId(@Param("id") String id);

    @Query("From RowEntity r where r.deleted = false and r.roomId in :ids")
    List<RowEntity> findRowByRoomIds(@Param("ids") List<String> ids);
}
