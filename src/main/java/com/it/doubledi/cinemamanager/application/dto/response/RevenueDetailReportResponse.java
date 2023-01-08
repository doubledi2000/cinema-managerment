package com.it.doubledi.cinemamanager.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevenueDetailReportResponse {
    private String locationCode;
    private String locationName;
    private Double drinkRevenue;
    private Double ticketRevenue;
    private Double totalRevenue;
}
