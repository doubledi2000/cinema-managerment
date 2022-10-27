package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.response.RowShowtimeResponse;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
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
    private Film film;
    private Room room;
    private List<RowShowtimeResponse> rows;

    public Showtime(ShowtimeCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.premiereDate = cmd.getPremiereDate();
        this.startAt = cmd.getStartAt();
        this.roomId = cmd.getRoomId();
        this.filmId = cmd.getFilmId();
        this.deleted = Boolean.FALSE;
        this.autoGenerateTicket = cmd.getAutoGenerateTicket();
        if (cmd.getAutoGenerateTicket()) {
            this.status = ShowtimeStatus.WAIT_ON_SALE;
        } else {
            this.status = ShowtimeStatus.WAIT_GEN_TICKET;
        }
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

    public void enrichRowShowtimeResponse(List<RowShowtimeResponse> rows) {
        if (!CollectionUtils.isEmpty(rows)) {
            this.rows = rows;
        } else {
            this.rows = new ArrayList<>();
        }
    }

    public void genTicket() {
        this.status = ShowtimeStatus.WAIT_ON_SALE;
    }
}
