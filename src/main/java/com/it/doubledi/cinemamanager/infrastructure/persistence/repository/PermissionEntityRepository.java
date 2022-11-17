package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, String> {

    @Query("From PermissionEntity p where p.deleted = false order by p.priority")
    List<PermissionEntity> findAll();

    @Query("from PermissionEntity p where p.deleted = false and p.id in :ids")
    List<PermissionEntity> findALlByIds(List<String> ids);
}
