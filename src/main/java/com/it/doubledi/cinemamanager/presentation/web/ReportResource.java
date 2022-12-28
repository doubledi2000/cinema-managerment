package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportByYearResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface ReportResource {

    @GetMapping("/reports/revenue-by-month")
    @PreAuthorize("hasPermission(null, 'report:view')")
    Response<List<RevenueReportByYearResponse>> revenueReportByYear(RevenueReportRequest request);

        @GetMapping("/reports/revenue")
    @PreAuthorize("hasPermission(null, 'report:view')")
    Response<List<RevenueReportResponse>> revenueReport(@Valid RevenueReportRequest request);

    @GetMapping("/reports/occupancy-rate-by-month")
    @PreAuthorize("hasPermission(null, 'report:view')")
    Response<List<OccupancyRateReportResponse>> occupancyRateReportByMonth(OccupancyRateReportRequest request);

    @GetMapping("/reports/occupancy")
    @PreAuthorize("hasPermission(null, 'report:view')")
    Response<List<OccupancyRateReportResponse>> occupancyRateReport(OccupancyRateReportRequest request);

}
