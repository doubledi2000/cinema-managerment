package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.domain.command.FilmScheduleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Showtime extends AuditableDomain {
    private String id;
    private LocalDate premiereDate;
    private Integer startAt;
    private Integer endAt;
    private String roomId;
    private String filmId;
    private Boolean deleted;
    private ShowtimeStatus status;
    private Boolean autoGenerateTicket;
    private String locationId;
    private Long version;

    private Location location;
    private Film film;
    private Room room;
    private List<RowShowtimeResponse> rows;
    private List<Ticket> tickets;

    public Showtime(ShowtimeCreateCmd cmd, FilmScheduleCreateCmd filmScheduleCreateCmd, FilmEntity filmEntity) {
        this.id = IdUtils.nextId();
        this.premiereDate = cmd.getPremierDate();
        this.roomId = cmd.getRoomId();
        this.deleted = Boolean.FALSE;
        this.status = ShowtimeStatus.WAIT_GEN_TICKET;
        this.autoGenerateTicket = Boolean.FALSE;
        this.filmId = filmScheduleCreateCmd.getFilmId();
        this.startAt = filmScheduleCreateCmd.getStartAt();
        this.endAt = filmScheduleCreateCmd.getStartAt() + filmEntity.getDuration();
    }

    public void calEndAt(int duration) {
        this.endAt = this.startAt + duration;
    }

    public void enrichFilm(Film film) {
        if (Objects.nonNull(film)) {
            this.film = film;
        }
    }

    public void enrichRoom(Room room) {
        if (Objects.nonNull(room)) {
            this.room = room;
        }
    }

    public void cancelBooking(String userId) {
        if (Objects.isNull(userId)) {
            return;
        }
        this.rows.forEach(r -> {
            r.getTickets().forEach(t -> {
                if (Objects.equals(userId, t.getUserSoldId()) && Objects.equals(TicketStatus.SELECTED, t.getStatus())) {
                    t.unselect();
                }
            });
        });
    }

    public long calTotalTicket() {
        return this.tickets.stream().filter(t -> !Objects.equals(t.getType(), ChairType.BOGY)).count();
    }

    public long calTotalTicketSold() {
        return this.tickets.stream().filter(t -> Objects.equals(t.getStatus(), TicketStatus.SOLD)).count();
    }

    public void cancel() {
        if (!ShowtimeStatus.WAIT_GEN_TICKET.equals(this.getStatus())) {
            throw new ResponseException(BadRequestError.CAN_NOT_CANCEL_SHOWTIME_WITH_STATUS_NOT_EQUAL_WAIT_GEN_TICKET);
        }
        this.status = ShowtimeStatus.CANCELED;
    }

    public void enrichLocation(Location location) {
        this.location = location;
    }

    public void enrichRowShowtimeResponse(List<RowShowtimeResponse> rows) {
        if (!CollectionUtils.isEmpty(rows)) {
            this.rows = rows;
        } else {
            this.rows = new ArrayList<>();
        }
    }

    public void enrichTicket(List<Ticket> tickets) {
        if (!CollectionUtils.isEmpty(tickets)) {
            this.tickets = tickets;
        } else {
            this.tickets = new ArrayList<>();
        }
    }


    public void genTicket() {
        this.status = ShowtimeStatus.WAIT_ON_SALE;
    }

    public void finish() {
        this.status = ShowtimeStatus.FINISH;
        if (!CollectionUtils.isEmpty(this.rows)) {
            this.getRows().forEach(r -> {
                r.getTickets().forEach(t -> {
                    if (Objects.equals(t.getStatus(), TicketStatus.SELECTED)
                            || Objects.equals(t.getStatus(), TicketStatus.AVAILABLE)) {
                        t.finish();
                    }
                });
            });
        }
    }
}
