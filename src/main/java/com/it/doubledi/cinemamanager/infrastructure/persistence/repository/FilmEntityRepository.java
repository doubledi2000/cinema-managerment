package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.FilmRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmEntityRepository extends JpaRepository<FilmEntity, String>, FilmRepositoryCustom {

    @Query("From FilmEntity f where f.deleted = false and f.id = :id")
    Optional<FilmEntity> findFilmById(String id);

    @Query("from FilmEntity f where f.deleted = false and f.id in :ids")
    List<FilmEntity> findByIds(List<String> ids);

    @Query("From FilmEntity f where f.deleted = false and f.status in :statuses")
    List<FilmEntity> findFilmByStatuses(@Param("statuses") List<FilmStatus> statuses);
}
