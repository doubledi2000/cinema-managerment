package com.it.doubledi.cinemamanager.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevenueReportResponse {
    private String locationId;
    private String locationName;
    private String locationCode;
    private double totalTicketRevenue;
    private double totalDrinkRevenue;
    private double totalRevenue;
}
