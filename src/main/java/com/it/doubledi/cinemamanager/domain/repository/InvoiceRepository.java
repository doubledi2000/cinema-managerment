package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.Invoice;

import java.util.List;

public interface InvoiceRepository extends DomainRepository<Invoice, String> {

    List<Invoice> enrichList(List<Invoice> invoices);
}
