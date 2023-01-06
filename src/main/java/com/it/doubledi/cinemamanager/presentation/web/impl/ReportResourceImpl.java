package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportByYearResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import com.it.doubledi.cinemamanager.application.service.ReportService;
import com.it.doubledi.cinemamanager.presentation.web.ReportResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReportResourceImpl implements ReportResource {

    private final ReportService reportService;

    @Override
    public Response<List<RevenueReportByYearResponse>> revenueReportByYear(RevenueReportRequest request) {
        return null;
    }

    @Override
    public Response<List<RevenueReportResponse>> revenueReport(RevenueReportRequest request) {
        return Response.of(reportService.revenueReport(request));
    }

    @Override
    public Response<List<OccupancyRateReportResponse>> occupancyRateReportDetail(OccupancyRateReportRequest request) {
        return null;
    }

    @Override
    public Response<List<OccupancyRateReportResponse>> occupancyRateReport(OccupancyRateReportRequest request) {
        return Response.of(reportService.occupancyRate(request));
    }
}
