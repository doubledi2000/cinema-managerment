package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class PriceByTimeCreateRequest extends Request {
    private String id;

    @NotNull(message = "START_AT_REQUIRED")
    private Integer startAt;

    @NotNull(message = "END_AT_REQUIRED")
    private Integer endAt;

    @NotNull(message = "PRICE_REQUIRED")
    private Float price;

    @NotNull(message = "TICKET_TYPE_REQUIRED")
    private TicketType ticketType;
}
