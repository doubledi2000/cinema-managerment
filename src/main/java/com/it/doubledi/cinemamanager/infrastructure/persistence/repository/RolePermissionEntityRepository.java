package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionEntityRepository extends JpaRepository<RolePermissionEntity, String> {
}
