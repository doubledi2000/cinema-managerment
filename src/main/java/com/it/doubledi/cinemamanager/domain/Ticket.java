package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.application.dto.request.TicketBookingRequest;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

@Data
@Builder
public class Ticket extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String chairId;
    private String showtimeId;
    private Double price;
    private TicketStatus status;
    private ChairType type;
    private String filmId;
    private String roomId;
    private Instant boughtAt;
    private String userSoldId;
    private String rowId;
    private Integer rowNumber;
    private String rowName;
    private Integer serialOfChair;
    private Boolean deleted;

    public boolean select(TicketBookingRequest request) {
        if (Objects.equals(this.getStatus(), TicketStatus.AVAILABLE)) {
            this.status = TicketStatus.SELECTED;
            this.userSoldId = request.getUserId();
            return true;
        }
        return false;
    }

    public boolean unselect(TicketBookingRequest request) {
        if (Objects.equals(this.getStatus(), TicketStatus.SELECTED) && Objects.equals(this.userSoldId, request.getUserId())) {
            this.status = TicketStatus.AVAILABLE;
            this.userSoldId = null;
            return true;
        }
        return false;
    }

    public void unselect(){
        if(Objects.equals(this.getStatus(), TicketStatus.SELECTED)) {
            this.status = TicketStatus.AVAILABLE;
            this.userSoldId = null;
        }
    }

    public void sold() {
        this.status = TicketStatus.SOLD;
    }

    public void finish(){
        this.status = TicketStatus.UNAVAILABLE;
    }
}
