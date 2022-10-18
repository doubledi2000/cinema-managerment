package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, String> {

    @Query("select ur from UserRoleEntity ur where ur.deleted = false and ur.userId = :userId and ur.roleId = :roleId")
    Optional<UserRoleEntity> findByUserAndRole(String userId, String roleId);
}
