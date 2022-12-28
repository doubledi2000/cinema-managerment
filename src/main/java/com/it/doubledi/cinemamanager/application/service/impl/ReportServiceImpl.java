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
import com.it.doubledi.cinemamanager.domain.*;
import com.it.doubledi.cinemamanager.domain.repository.InvoiceRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TicketEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.*;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ReportType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.util.*;
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
    private final FilmEntityRepository filmEntityRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final ShowtimeEntityRepository showtimeEntityRepository;
    private final ShowtimeEntityMapper showtimeEntityMapper;
    private final TicketEntityRepository ticketEntityRepository;
    private final TicketEntityMapper ticketEntityMapper;

    @Override
    public List<RevenueReportResponse> revenueReport(RevenueReportRequest request) {
        List<Location> locations = this.getLocations(request.getLocationIds());
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        if (Objects.equals(request.getType(), ReportType.MONTH)) {
            request.setStartAt(DateUtils.getFirstDayOfMonth(Instant.now(Clock.system(ZoneId.systemDefault()))));
            request.setEndAt(DateUtils.getLastDayOfMonth(Instant.now(Clock.system(ZoneId.systemDefault()))));
        }
        List<String> locationIds = userAuthentication.isRoot() ? null : locations.stream().map(Location::getId).collect(Collectors.toList());
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
    public List<OccupancyRateReportResponse> occupancyRate(OccupancyRateReportRequest request) {
        List<FilmEntity> filmEntities = this.filmEntityRepository.findByIdsAndStatuses(request.getFilmIds(), List.of(FilmStatus.APPROVED));
        List<Film> films = this.filmEntityMapper.toDomain(filmEntities);
        List<String> filmIds = films.stream().map(Film::getId).collect(Collectors.toList());
        List<ShowtimeEntity> showtimeEntities = this.showtimeEntityRepository.findAllByFilmIdsAndStatuses(filmIds, List.of(ShowtimeStatus.WAIT_ON_SALE, ShowtimeStatus.FINISH));
        List<Showtime> showtimes = this.showtimeEntityMapper.toDomain(showtimeEntities);
        this.enrichTickets(showtimes);
        List<OccupancyRateReportResponse> responses = new ArrayList<>();
        films.forEach(f -> {
            List<Showtime> showtimeTmps = showtimes.stream().filter(s -> Objects.equals(s.getFilmId(), f.getId())).collect(Collectors.toList());
            Long totalTicket = showtimeTmps.stream().map(Showtime::calTotalTicket).reduce(0L, Long::sum);
            Long totalTicketSold = showtimeTmps.stream().map(Showtime::calTotalTicketSold).reduce(0L, Long::sum);
            responses.add(OccupancyRateReportResponse.builder()
                    .filmId(f.getId())
                    .filmName(f.getName())
                    .filmCode(f.getCode())
                    .totalTicket(totalTicket)
                    .totalTicketWasSold(totalTicketSold)
                    .build());
        });
        return responses;
    }

    private void enrichTickets(List<Showtime> showtimes) {
        List<String> showtimeIds = showtimes.stream().map(Showtime::getId).collect(Collectors.toList());
        List<TicketEntity> ticketEntities = this.ticketEntityRepository.findAllByShowtimeIds(showtimeIds);
        List<Ticket> tickets = this.ticketEntityMapper.toDomain(ticketEntities);
        showtimes.forEach(s -> {
            List<Ticket> ticketsTmp = tickets.stream().filter(t -> Objects.equals(t.getShowtimeId(), s.getId())).collect(Collectors.toList());
            s.enrichTicket(ticketsTmp);
        });

    }
}
