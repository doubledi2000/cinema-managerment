package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketEntityRepository extends JpaRepository<TicketEntity, String> {
    @Query("From TicketEntity t where t.deleted = false and t.showtimeId in :ids")
    List<TicketEntity> findAllByShowtimeIds(List<String> ids);
}
