package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.TypeOfFilmRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TypeOfFilmStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeOfFilmEntityRepository extends JpaRepository<TypeOfFilmEntity, String>, TypeOfFilmRepositoryCustom {
    @Query("FROM TypeOfFilmEntity tof where tof.deleted = false and tof.code = :code")
    Optional<TypeOfFilmEntity> findByCode(String code);

    @Query("from TypeOfFilmEntity  tof where tof.deleted = false " +
            " and (:keyword is null or tof.name like :keyword or tof.code like :keyword) " +
            " and (coalesce(:status) is null or tof.status in :statuses) " +
            " order by tof.name asc ")
    Page<TypeOfFilmEntity> autoComplete(@Param("keyword") String keyword, @Param("statuses") List<TypeOfFilmStatus> statuses, Pageable pageable);

    @Query("from TypeOfFilmEntity tof where tof.deleted = false and tof.id in :ids")
    List<TypeOfFilmEntity> findAllTypeByIds(List<String> ids);

}
