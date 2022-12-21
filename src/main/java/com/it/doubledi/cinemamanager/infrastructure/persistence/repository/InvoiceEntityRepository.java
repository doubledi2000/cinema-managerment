package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InvoiceEntityRepository extends JpaRepository<InvoiceEntity, String> {

    @Query("From InvoiceEntity i where i.paymentTime >= :startAt and i.paymentTime <= :endAt " +
            " and coalesce(:locationIds, null) is null or i.locationId in :locationIds")
    List<InvoiceEntity> findInvoiceByTime(Instant startAt, Instant endAt, List<String> locationIds);
}
