package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.InvoiceCreateRequest;
import com.it.doubledi.cinemamanager.domain.Invoice;

public interface BookingService {
    Invoice booking(InvoiceCreateRequest request);

    Invoice getById(String id);
}
