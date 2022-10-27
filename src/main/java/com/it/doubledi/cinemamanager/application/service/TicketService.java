package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.GenerateTicketRequest;
import com.it.doubledi.cinemamanager.domain.Room;
import com.it.doubledi.cinemamanager.domain.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> generateTickets(Room room);
}
