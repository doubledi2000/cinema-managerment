package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceByTimeCreateCmd {
    private String id;
    private Integer startAt;
    private Integer endAt;
    private Float price;
    private TicketType ticketType;
}
