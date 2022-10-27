package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.GenerateTicketRequest;
import com.it.doubledi.cinemamanager.application.service.TicketService;
import com.it.doubledi.cinemamanager.presentation.web.TicketResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TicketResourceImpl implements TicketResource {

//    private final TicketService ticketService;
//
//    @Override
//    public Response<Boolean> generateTickets(GenerateTicketRequest request) {
//        ticketService.generateTickets(request);
//        return Response.ok();
//    }
}
