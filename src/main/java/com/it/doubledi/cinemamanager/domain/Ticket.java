package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Ticket extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String chairId;
    private String showtimeId;
    private BigDecimal price;
    private TicketStatus status;
    private TicketType type;
    private String filmId;
    private String roomId;
    private Instant boughtAt;
    private String userSoldId;
}
