package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmProducerEntityRepository extends JpaRepository<FilmProducerEntity, String> {

    @Query("from FilmProducerEntity fp where fp.deleted = false and fp.id in :ids")
    List<FilmProducerEntity> findAllByIds(List<String> ids);

    @Query("from FilmProducerEntity fp where fp.deleted = false and fp.filmId in :ids")
    List<FilmProducerEntity> findAllByFilmIds(List<String> ids);
}
