package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.LocationRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationEntityRepository extends JpaRepository<LocationEntity, String>, LocationRepositoryCustom {
    @Query("from LocationEntity l where l.deleted = false " +
            " and (:keyword is null or l.name like :keyword or l.code like :keyword) " +
            " and (COALESCE(:statuses, null) is null or l.status in :statuses) " +
            " order by l.name asc ")
    Page<LocationEntity> autoComplete(@Param("keyword") String keyword, @Param("statuses") List<LocationStatus> statuses, Pageable pageable);

    @Query("From LocationEntity l where l.deleted = false and l.id in ids")
    List<LocationEntity> findByIds(@Param("id") List<String> ids);
}
