package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.InvoiceCreateRequest;
import com.it.doubledi.cinemamanager.application.service.BookingService;
import com.it.doubledi.cinemamanager.domain.Invoice;
import com.it.doubledi.cinemamanager.presentation.web.BookingResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookingResourceImpl implements BookingResource {

    private final BookingService bookingService;

    @Override
    public Response<Invoice> booking(InvoiceCreateRequest request) {
        return Response.of(bookingService.booking(request));
    }

    @Override
    public PagingResponse<Invoice> findInvoices() {
        return null;
    }

    @Override
    public Response<Invoice> getInvoiceById(String id) {
        return null;
    }
}
