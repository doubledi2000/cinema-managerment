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
public class RevenueReportResponse {
    private String locationId;
    private String locationName;
    private String locationCode;
    private double totalTicketRevenue;
    private double totalDrinkRevenue;
    private double totalRevenue;
    private LocalDate date;
}
