package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeEntityRepository extends JpaRepository<ShowtimeEntity, String> {
    @Query("from ShowtimeEntity s where s.deleted = false and s.id in :ids")
    List<ShowtimeEntity> findAllByIds(List<String> ids);
}
