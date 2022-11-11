package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLocationEntityRepository extends JpaRepository<UserLocationEntity, String> {

    @Query("From UserLocationEntity  UL where UL.deleted = false and UL.userId in :userIds")
    List<UserLocationEntity> findAllByUserIds(List<String> userIds);

}
