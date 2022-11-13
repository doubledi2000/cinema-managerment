package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolePermissionEntityRepository extends JpaRepository<RolePermissionEntity, String> {

    @Query("From RolePermissionEntity rp where rp.roleId in :roleIds and rp.deleted = false")
    List<RolePermissionEntity> findAllByRoleIds(@Param("roleIds") List<String> roleIds);
}
