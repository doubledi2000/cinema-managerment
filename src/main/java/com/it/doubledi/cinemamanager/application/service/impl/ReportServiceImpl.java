package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import com.it.doubledi.cinemamanager.application.service.ReportService;
import com.it.doubledi.cinemamanager.domain.Invoice;
import com.it.doubledi.cinemamanager.domain.repository.InvoiceRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.InvoiceEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.InvoiceEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.InvoiceEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final InvoiceEntityRepository invoiceEntityRepository;
    private final InvoiceEntityMapper invoiceEntityMapper;
    private final InvoiceRepository invoiceRepository;

    @Override
    public List<RevenueReportResponse> revenueReport(RevenueReportRequest request) {
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        List<String> locationIds = null;
        if (userAuthentication.isRoot() || UserLevel.CENTER.equals(userAuthentication.getUserLevel())) {
            log.info("User have all location");
        } else if (!CollectionUtils.isEmpty(userAuthentication.getLocationIds())) {
            locationIds = userAuthentication.getLocationIds();
            if (!CollectionUtils.isEmpty(request.getLocationIds())) {
                locationIds.retainAll(request.getLocationIds());
            }
            if (CollectionUtils.isEmpty(locationIds)) {
                return new ArrayList<>();
            }
        } else {
            log.info("User have no location");
            return new ArrayList<>();
        }

        List<Invoice> invoices = this.invoiceEntityMapper.toDomain(
                this.invoiceEntityRepository.findInvoiceByTime(request.getStartAt(), request.getEndAt(), locationIds));
        this.invoiceRepository.enrichList(invoices);


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
