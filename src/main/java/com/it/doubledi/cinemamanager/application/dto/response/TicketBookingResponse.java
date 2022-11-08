package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketBookingResponse {
    private String ticketId;
    private String userId;
    private TicketStatus status;
    private Boolean success;
    private String message;
}
