package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.RoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, String>, RoleRepositoryCustom {

    @Query("Select r from RoleEntity r where r.deleted = false and r.code = :code")
    Optional<RoleEntity> findRoleByCode(String code);

    @Query("Select r from RoleEntity r where r.deleted = false and r.id in :ids")
    List<RoleEntity> findAllByIds(List<String> ids);
}
