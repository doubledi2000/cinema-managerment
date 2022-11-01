package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmTypeEntityRepository extends JpaRepository<FilmTypeEntity, String> {

    @Query("from FilmTypeEntity  ft where ft.deleted = false and ft.filmId in :filmIds")
    List<FilmTypeEntity> getAllFilmTypeByFilmIds(@Param("filmIds") List<String> filmIds);

    @Query("from FilmTypeEntity ft where ft.deleted = false and ft.typeId in :ids")
    List<FilmTypeEntity> findByTypeIds(@Param("ids") List<String> ids);
}
