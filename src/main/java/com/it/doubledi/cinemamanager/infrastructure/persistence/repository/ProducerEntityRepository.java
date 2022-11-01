package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.ProducerRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerEntityRepository extends JpaRepository<ProducerEntity, String>, ProducerRepositoryCustom {

    @Query("from ProducerEntity  p where p.deleted = false " +
            " and (:keyword is null or p.name like :keyword " +
            " or p.code like :keyword " +
            " or p.representative like :keyword " +
            " or p.nationally like :keyword) " +
            " order by p.name asc ")
    Page<ProducerEntity> autoComplete(@Param("keyword") String keyword, Pageable pageable);

    @Query("from ProducerEntity p where p.deleted = false and p.id in :ids")
    List<ProducerEntity> findByIds(@Param("ids") List<String> ids);

}
