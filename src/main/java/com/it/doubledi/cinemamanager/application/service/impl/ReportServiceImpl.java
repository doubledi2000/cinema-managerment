package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.UserAuthentication;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.util.DateUtils;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.dto.request.OccupancyRateReportRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportByYearRequest;
import com.it.doubledi.cinemamanager.application.dto.request.RevenueReportRequest;
import com.it.doubledi.cinemamanager.application.dto.response.OccupancyRateReportResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportByYearResponse;
import com.it.doubledi.cinemamanager.application.dto.response.RevenueReportResponse;
import com.it.doubledi.cinemamanager.application.service.ReportService;
import com.it.doubledi.cinemamanager.domain.Invoice;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.repository.InvoiceRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.InvoiceEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.LocationEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.InvoiceEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.LocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final InvoiceEntityRepository invoiceEntityRepository;
    private final InvoiceEntityMapper invoiceEntityMapper;
    private final InvoiceRepository invoiceRepository;
    private final LocationEntityMapper locationEntityMapper;
    private final LocationEntityRepository locationEntityRepository;

    @Override
    public List<RevenueReportResponse> revenueReport(RevenueReportRequest request) {
        List<Location> locations = this.getLocations(request.getLocationIds());
        List<String> locationIds = locations.stream().map(Location::getId).collect(Collectors.toList());

        List<Invoice> invoices = this.invoiceEntityMapper.toDomain(
                this.invoiceEntityRepository.findInvoiceByTime(request.getStartAt(), request.getEndAt(), locationIds));
        this.invoiceRepository.enrichList(invoices);
        List<RevenueReportResponse> responses = new ArrayList<>();

        locations.forEach(l -> {
            List<Invoice> invoicesTmp = invoices.stream().filter(i -> Objects.equals(i.getLocationId(), l.getId())).collect(Collectors.toList());
            double totalTicketRevenue = invoicesTmp.stream().map(Invoice::calTotalTicketRevenue).reduce(0d, Double::sum);
            double totalDrinkRevenue = invoicesTmp.stream().map(Invoice::calTotalDrinkRevenue).reduce(0d, Double::sum);
            double totalRevenue = totalTicketRevenue + totalDrinkRevenue;
            RevenueReportResponse revenueReportResponse = RevenueReportResponse.builder()
                    .locationId(l.getId())
                    .locationCode(l.getCode())
                    .locationName(l.getName())
                    .totalTicketRevenue(totalTicketRevenue)
                    .totalDrinkRevenue(totalDrinkRevenue)
                    .totalRevenue(totalRevenue)
                    .build();
            responses.add(revenueReportResponse);
        });
        return responses;
    }

    @Override
    public List<RevenueReportByYearResponse> revenueReportByYear(RevenueReportByYearRequest request) {
        SecurityUtils.checkPermissionOfLocation(request.getLocationId());
        Instant startAt = DateUtils.getFirstDayOfYear(request.getDate());
        Instant endAt = DateUtils.getLastDayOfYear(request.getDate());
        List<Invoice> invoices = this.invoiceEntityMapper.toDomain(this.invoiceEntityRepository.findInvoiceByTime(startAt, endAt, List.of(request.getLocationId())));
        List<RevenueReportByYearResponse> responses = new ArrayList<>();
        for (Month m : Month.values()) {
            List<Invoice> invoiceTmps = invoices.stream().filter(i -> Objects.equals(GregorianCalendar.from(ZonedDateTime.from(i.getPaymentTime())).get(Calendar.MONTH), m.getValue())).collect(Collectors.toList());
            double totalDrinkRevenue = invoiceTmps.stream().map(Invoice::calTotalDrinkRevenue).reduce(0d, Double::sum);
            double totalTicketRevenue = invoiceTmps.stream().map(Invoice::calTotalTicketRevenue).reduce(0d, Double::sum);
            double totalRevenue = totalDrinkRevenue + totalTicketRevenue;
            responses.add(RevenueReportByYearResponse.builder()
                    .month(m)
                    .totalDrinkRevenue(totalDrinkRevenue)
                    .totalTicketRevenue(totalTicketRevenue)
                    .totalRevenue(totalRevenue)
                    .build());
        }
        return responses;
    }

    private List<Location> getLocations(List<String> locationIds) {
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        List<String> locationIdsTmp = null;
        if (userAuthentication.isRoot() || UserLevel.CENTER.equals(userAuthentication.getUserLevel())) {
            log.info("User have all location");
        } else if (!CollectionUtils.isEmpty(userAuthentication.getLocationIds())) {
            locationIdsTmp = userAuthentication.getLocationIds();
            if (!CollectionUtils.isEmpty(locationIds)) {
                locationIdsTmp.retainAll(locationIds);
            }
            if (CollectionUtils.isEmpty(locationIdsTmp)) {
                throw new ResponseException(BadRequestError.USER_HAS_NO_LOCATION);
            }
        } else {
            log.info("User have no location");
            throw new ResponseException(BadRequestError.USER_HAS_NO_LOCATION);
        }
        List<Location> locations;
        if (!CollectionUtils.isEmpty(locationIdsTmp)) {
            locations = this.locationEntityMapper.toDomain(this.locationEntityRepository.findByIds(locationIdsTmp));
        } else {
            locations = this.locationEntityMapper.toDomain(this.locationEntityRepository.findAllLocation());
        }
        return locations;
    }

    @Override
    public List<OccupancyRateReportResponse> occupancyRateReport(OccupancyRateReportRequest request) {
        return null;
    }

    @Override
    public List<OccupancyRateReportResponse> occupancyRateReportByLocation(OccupancyRateReportRequest request) {
        return null;
    }

    @Override
    public List<OccupancyRateReportResponse> occupancyRateLast15DaysReport() {
        return null;
    }
}
