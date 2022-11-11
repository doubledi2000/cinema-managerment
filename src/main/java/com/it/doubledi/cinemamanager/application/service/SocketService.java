package com.it.doubledi.cinemamanager.application.service;


import com.it.doubledi.cinemamanager.application.dto.request.TicketBookingRequest;
import com.it.doubledi.cinemamanager.application.dto.response.TicketBookingResponse;

public interface SocketService {
    TicketBookingResponse pickTicket(String id, TicketBookingRequest request);
}
