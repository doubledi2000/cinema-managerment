package com.it.doubledi.cinemamanager.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OccupancyRateDetailReportResponse {
    private String locationCode;
    private String locationName;
    private String filmCode;
    private String filmName;
    private long totalNormalTicket;
    private long totalNormalTicketWasSold;
    private long totalVIPTicket;
    private long totalVIPTicketWasSold;
    private long totalSweetTicket;
    private long totalSweetTicketWasSold;
}
