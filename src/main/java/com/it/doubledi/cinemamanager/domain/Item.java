package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends AuditableDomain {
    private String id;
    private ItemType type;
    private Integer quantity;
    private Double price;
    private String itemId;
    private String itemName;
    private Boolean deleted;
    private String invoiceId;

    public Item(Ticket ticket, String invoiceId) {
        this.id = IdUtils.nextId();
        this.type = ItemType.TICKET;
        this.quantity = 1;
        this.price = ticket.getPrice();
        this.itemId = ticket.getId();
        this.itemName = ticket.getName();
        this.deleted = Boolean.FALSE;
        this.invoiceId = invoiceId;
    }

    public Item(Drink drink, int quantity, String invoiceId) {
        this.id = IdUtils.nextId();
        this.type = ItemType.DRINK;
        this.quantity = quantity;
        this.price = drink.getPrice();
        this.itemId = drink.getId();
        this.itemName = drink.getName();
        this.deleted = Boolean.FALSE;
        this.invoiceId = invoiceId;
    }
}
