package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.RoleRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, String>, RoleRepositoryCustom {

    @Query("Select r from RoleEntity r where r.deleted = false and r.code = :code")
    Optional<RoleEntity> findRoleByCode(String code);

    @Query("Select r from RoleEntity r where r.deleted = false and r.id in :ids")
    List<RoleEntity> findAllByIds(List<String> ids);

    @Query("Select r from RoleEntity r where r.deleted = false and r.status = :status and (:keyword is null or r.name like :keyword or r.code like :keyword)")
    Page<RoleEntity> autoComplete(@Param("keyword") String keyword, @Param("status") RoleStatus status, Pageable pageable);
}
