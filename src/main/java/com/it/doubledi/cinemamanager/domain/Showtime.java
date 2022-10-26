package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ShowtimeCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
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

    public Showtime(ShowtimeCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.premiereDate = cmd.getPremiereDate();
        this.startAt = cmd.getStartAt();
        this.roomId = cmd.getRoomId();
        this.filmId = cmd.getFilmId();
        this.deleted = Boolean.FALSE;
        this.autoGenerateTicket = cmd.getAutoGenerateTicket();
        if(cmd.getAutoGenerateTicket()) {
            this.status = ShowtimeStatus.WAIT_ON_SALE;
        }else {
            this.status = ShowtimeStatus.WAIT_GEN_TICKET;
        }

    }
}
