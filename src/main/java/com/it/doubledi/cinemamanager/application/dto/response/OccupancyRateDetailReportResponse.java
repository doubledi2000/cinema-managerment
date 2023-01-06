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
    private int totalNormalTicket;
    private int totalNormalTicketWasSold;
    private int totalVIPTicket;
    private int totalVIPTicketWasSold;
    private int totalSweetTicket;
    private int totalSweetTicketWasSold;
}
