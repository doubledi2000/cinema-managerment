package com.it.doubledi.cinemamanager.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevenueReportByYearResponse {
    private Month month;
    private double totalDrinkRevenue;
    private double totalTicketRevenue;
    private double totalRevenue;
}
