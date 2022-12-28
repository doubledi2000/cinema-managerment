package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportByYearRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportByYearResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;

import java.util.List;

public interface ReportService {
    List<RevenueReportResponse> revenueReport(RevenueReportRequest request);

    List<RevenueReportByYearResponse> revenueReportByYear(RevenueReportByYearRequest request);

    List<OccupancyRateReportResponse> occupancyRateReport(OccupancyRateReportRequest request);

    List<OccupancyRateReportResponse> occupancyRateReportByLocation(OccupancyRateReportRequest request);

    List<OccupancyRateReportResponse> occupancyRate(OccupancyRateReportRequest request);
}
