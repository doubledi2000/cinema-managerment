package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.domain.Ticket;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RowShowtimeResponse {
    private Integer rowNumber;
    private String rowName;
    private List<Ticket> tickets;
}
