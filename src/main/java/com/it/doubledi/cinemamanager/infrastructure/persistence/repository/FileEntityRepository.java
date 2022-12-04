package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileEntityRepository extends JpaRepository<FileEntity, String> {

    @Query("From FileEntity f where f.id = :id and f.deleted = false")
    FileEntity getById(@Param("id") String id);

    @Query("From FileEntity f where f.id in :ids and f.deleted = false")
    List<FileEntity> findByIds(List<String> ids);
}
