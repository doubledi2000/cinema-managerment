package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import com.it.doubledi.cinemamanager.application.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Override
    public List<RevenueReportResponse> revenueReport(RevenueReportRequest request) {
        return null;
    }

    @Override
    public List<RevenueReportResponse> revenueReportByMonth(RevenueReportRequest request) {
        return null;
    }

    @Override
    public List<OccupancyRateReportResponse> occupancyRateReport(OccupancyRateReportRequest request) {
        return null;
    }

    @Override
    public List<OccupancyRateReportResponse> occupancyRateReportByMonth(OccupancyRateReportRequest request) {
        return null;
    }
}
