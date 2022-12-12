package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.PagingResponse;
import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.InvoiceCreateRequest;
import com.it.doubledi.cinemamanager.domain.Invoice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface BookingResource {

    @PostMapping("/bookings")
    Response<Invoice> booking(@RequestBody InvoiceCreateRequest request);

    @GetMapping("/invoices")
    PagingResponse<Invoice> findInvoices();

    @GetMapping("/invoices/{id}")
    Response<Invoice> getInvoiceById(@PathVariable("id") String id);
}
