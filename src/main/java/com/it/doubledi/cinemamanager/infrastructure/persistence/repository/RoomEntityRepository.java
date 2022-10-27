package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomEntityRepository extends JpaRepository<RoomEntity, String> {
    @Query("From RoomEntity r where r.deleted = false and r.id = :id")
    Optional<RoomEntity> findById(String id);

    @Query("From RoomEntity r where r.deleted = false and r.id in :ids")
    List<RoomEntity> findByIds(List<String> ids);
}
