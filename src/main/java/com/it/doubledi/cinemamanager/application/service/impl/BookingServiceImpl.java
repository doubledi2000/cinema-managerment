package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager.application.dto.request.InvoiceCreateRequest;
import com.it.doubledi.cinemamanager.application.service.BookingService;
import com.it.doubledi.cinemamanager.domain.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {


    @Override
    @Transactional
    public Invoice booking(InvoiceCreateRequest request) {
        return null;
    }
}
