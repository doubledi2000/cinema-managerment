package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.RoomRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomEntityRepository extends JpaRepository<RoomEntity, String>, RoomRepositoryCustom {
    @Query("From RoomEntity r where r.deleted = false and r.id = :id")
    Optional<RoomEntity> findById(String id);

    @Query("From RoomEntity r where r.deleted = false and r.id in :ids")
    List<RoomEntity> findByIds(List<String> ids);

    @Query("from RoomEntity  r where r.deleted = false " +
            " and coalesce(:locationIds,null) is null or r.locationId in :locationIds " +
            " and (:keyword is null or r.name like :keyword or r.code like :keyword) " +
            " and (coalesce(:statuses,null) is null or r.status in :statuses) " +
            " order by r.name asc ")
    Page<RoomEntity> autoComplete(@Param("locationIds") List<String> locationIds, @Param("keyword") String keyword, @Param("statuses") List<RoomStatus> statuses, Pageable pageable);

    @Query("from RoomEntity r where r.deleted = false and coalesce(:locationIds, null) is null or r.locationId in :locationIds")
    List<RoomEntity> findByLocationIds(List<String> locationIds);

    @Query("from RoomEntity r where r.deleted = false and (coalesce(:locationIds, null) is null or r.locationId in :locationIds) and r.status = :status")
    List<RoomEntity> findByLocationIdsAndStatus(List<String> locationIds, RoomStatus status);


}
