package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//@SuperBuilder
public class PriceByTime extends AuditableDomain {
    private String id;
    private Integer startAt;
    private Integer endAt;
    private Float price;
    private TicketType ticketType;
    private String configPriceId;
    private Integer priority;
    private Boolean deleted;

    public PriceByTime(String configPriceId, PriceByTimeCreateCmd cmd) {
        this.id = Objects.nonNull(cmd.getId()) ? cmd.getId() : IdUtils.nextId();
        this.startAt = cmd.getStartAt();
        this.endAt = cmd.getEndAt();
        this.price = cmd.getPrice();
        this.ticketType = cmd.getTicketType();
        switch (this.ticketType) {
            case BOGY:
                this.priority = 0;
                break;
            case NORMAL:
                this.priority = 1;
                break;
            case VIP:
                this.priority = 2;
                break;
            case SWEET:
                this.priority = 4;
                break;
        }
        this.configPriceId = configPriceId;
        this.deleted = Boolean.FALSE;
    }

    public void update(PriceByTimeCreateCmd cmd) {
        this.deleted = Boolean.FALSE;
        this.price = cmd.getPrice();
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void undelete() {
        this.deleted = Boolean.FALSE;
    }
}
