package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TicketResponse {
    private String id;
    private String code;
    private String name;
    private Float price;
    private TicketStatus status;
    private ChairType type;
    private Instant boughtAt;
    private String userSoldId;
    private Integer rowNumber;
    private String rowName;
    private Integer serialOfChair;
}
