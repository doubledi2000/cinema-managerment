package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ConfigPriceCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.PriceByTimeCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class PriceByTime extends AuditableEntity {
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
}
