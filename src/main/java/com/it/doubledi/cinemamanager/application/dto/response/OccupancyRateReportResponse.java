package com.it.doubledi.cinemamanager.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OccupancyRateReportResponse {
    private String filmId;
    private String filmName;
    private String filmCode;
    private int totalTicket;
    private int totalTicketWasSold;
    private LocalDate startAt;
    private LocalDate endAt;
}
