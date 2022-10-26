package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.PriceCreateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price extends AuditableDomain {
    private String id;
    private Float price;
    private TicketType ticketType;
    private Integer priority;
    private Boolean deleted;
    private String priceByTimeId;

    public Price(String priceByTimeId, PriceCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.price = cmd.getPrice();
        this.priceByTimeId = priceByTimeId;
        switch (cmd.getTicketType()) {
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
                this.priority = 3;
                break;
        }
    }

    public Price(String priceByTimeId, TicketType ticketType){
        this.id = IdUtils.nextId();
        this.price = Constant.TICKET_DEFAULT_PRICE;
        this.deleted = Boolean.FALSE;
        this.ticketType = ticketType;
        this.defaultPriority();
        this.priceByTimeId = priceByTimeId;
    }

    public void update(PriceCreateCmd cmd) {
        this.price = cmd.getPrice();
        this.deleted = Boolean.FALSE;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void undelete() {
        this.deleted = Boolean.FALSE;
    }

    private void defaultPriority(){
        switch (this.getTicketType()){
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
                this.priority = 3;
                break;
        }
    }

}
