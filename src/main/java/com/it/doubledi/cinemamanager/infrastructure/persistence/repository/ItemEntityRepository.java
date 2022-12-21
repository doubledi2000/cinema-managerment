package com.it.doubledi.cinemamanager.infrastructure.persistence.repository;

import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, String> {

    @Query("From ItemEntity i where i.invoiceId in :invoiceIds")
    List<ItemEntity> findAllByInvoiceIds(@Param("invoiceIds") List<String> invoiceIds);
}
