package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.UserRepositoryCustom;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String>, UserRepositoryCustom {
    @Query("select u from UserEntity u where u.deleted = false and u.username = :username")
    Optional<UserEntity> findUserByUsername(String username);

    @Query("FROM UserEntity U WHERE U.deleted = false and U.employeeCode = :code")
    Optional<UserEntity> findByCode(String code);

    @Query("From UserEntity U WHERE U.deleted = false and U.phoneNumber = :phoneNumber")
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    @Query("From UserEntity U WHERE U.deleted = false and U.email = :email")
    Optional<UserEntity> findByEmail(String email);


    @Query("from UserEntity  u where u.deleted = false " +
            " and (:keyword is null or u.fullName like :keyword or u.employeeCode like :keyword or u.phoneNumber like :keyword) " +
            " and (coalesce(:statuses,null) is null or u.status in :statuses) " +
            " and (coalesce(:userIds,null) is null or u.id in :ids )" +
            " order by u.fullName asc ")
    Page<UserEntity> autoComplete(@Param("keyword") String keyword, @Param("statuses") List<UserStatus> statuses, @Param("ids") List<String> ids, Pageable pageable);
}
