package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.TicketBookingRequest;
import com.it.doubledi.cinemamanager.application.dto.response.TicketBookingResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public interface SocketController {

    @MessageMapping("/hello/{id}")
    @SendTo("/topic/greetings/{id}")
    Response<TicketBookingResponse> pickTicket(@DestinationVariable("id") String id, TicketBookingRequest request);
}
