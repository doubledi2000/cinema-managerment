package com.it.doubledi.cinemamanager.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OccupancyRateReportResponse {
    private String filmId;
    private String filmName;
    private String filmCode;
    private long totalTicket;
    private long totalTicketWasSold;
}
