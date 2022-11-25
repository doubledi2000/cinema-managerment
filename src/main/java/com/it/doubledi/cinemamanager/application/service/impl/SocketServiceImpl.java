package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.exception.AuthenticationError;
import com.it.doubledi.cinemamanager._common.model.exception.AuthorizationError;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager.application.config.SecurityUtils;
import com.it.doubledi.cinemamanager.application.dto.request.TicketBookingRequest;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.application.dto.response.TicketBookingResponse;
import com.it.doubledi.cinemamanager.application.service.SocketService;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.domain.Ticket;
import com.it.doubledi.cinemamanager.domain.repository.ShowtimeRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketBookingType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocketServiceImpl implements SocketService {
    private final ShowtimeRepository showtimeRepository;

    @Override
    public TicketBookingResponse pickTicket(String id, TicketBookingRequest request) {
        TicketBookingRequest newRequset = new TicketBookingRequest(id);
        Showtime showtime = this.showtimeRepository.getById(newRequset.getShowtimeId());
        boolean isChangeStatus = false;
        Ticket ticket = this.getTicketFromShowtime(showtime, newRequset.getTicketId());
        if (Objects.isNull(ticket)) {
            return TicketBookingResponse.builder()
                    .success(false)
                    .message("Ticket not found")
                    .ticketId(newRequset.getTicketId())
                    .build();
        }
        String message = "";
        if (Objects.equals(newRequset.getType(), TicketBookingType.SELECT)) {
            isChangeStatus = ticket.select(newRequset);
            if (isChangeStatus) {
                message = "Chọn vé thành công";
            } else {
                message = "Chọn vé không thành công";
            }
        } else {
            isChangeStatus = ticket.unselect(newRequset);
            if (isChangeStatus) {
                message = "Hủy chọn vé thành công";
            } else {
                message = "Hủy chọn vé không thành công";
            }
        }
        if (isChangeStatus) this.showtimeRepository.save(showtime);

        return TicketBookingResponse.builder()
                .ticketId(newRequset.getTicketId())
                .userId(newRequset.getUserId())
                .status(ticket.getStatus())
                .success(isChangeStatus)
                .message(message)
                .build();

    }

    private Ticket getTicketFromShowtime(Showtime showtime, String ticketId) {
        for (RowShowtimeResponse row : showtime.getRows()) {
            for (Ticket ticket : row.getTickets()) {
                if (Objects.equals(ticket.getId(), ticketId)) {
                    return ticket;
                }
            }
        }
        return null;
    }

}
