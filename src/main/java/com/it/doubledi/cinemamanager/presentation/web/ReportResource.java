package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface ReportResource {

    @GetMapping("/reports/revenue-by-month")
    Response<List<RevenueReportResponse>> revenueReportByMonth(RevenueReportRequest request);

    @GetMapping("/reports/revenue")
    Response<List<RevenueReportResponse>> revenueReportByDay(RevenueReportRequest request);

    @GetMapping("/reports/occupancy-rate-by-month")
    Response<List<OccupancyRateReportResponse>> occupancyRateReportByMonth(OccupancyRateReportRequest request);

    @GetMapping("/reports/occupancy")
    Response<List<OccupancyRateReportResponse>> occupancyRateReportByDay(OccupancyRateReportRequest request);

}
