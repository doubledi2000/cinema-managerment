package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import com.it.doubledi.cinemamanager.presentation.web.ReportResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReportResourceImpl implements ReportResource {


    @Override
    public Response<List<RevenueReportResponse>> revenueReportByMonth(RevenueReportRequest request) {
        return null;
    }

    @Override
    public Response<List<RevenueReportResponse>> revenueReportByDay(RevenueReportRequest request) {
        return null;
    }

    @Override
    public Response<List<OccupancyRateReportResponse>> occupancyRateReportByMonth(OccupancyRateReportRequest request) {
        return null;
    }

    @Override
    public Response<List<OccupancyRateReportResponse>> occupancyRateReportByDay(OccupancyRateReportRequest request) {
        return null;
    }
}
