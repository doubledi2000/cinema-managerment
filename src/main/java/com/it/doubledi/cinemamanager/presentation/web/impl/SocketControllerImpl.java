package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.TicketBookingRequest;
import com.it.doubledi.cinemamanager.application.dto.response.TicketBookingResponse;
import com.it.doubledi.cinemamanager.application.service.SocketService;
import com.it.doubledi.cinemamanager.presentation.web.SocketController;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SocketControllerImpl implements SocketController {
    private final SimpMessagingTemplate websocket;
    private final SocketService socketService;

    @Override
    public Response<TicketBookingResponse> pickTicket(String id, TicketBookingRequest request) {

        return Response.of(this.socketService.pickTicket(id, request));
    }
}
