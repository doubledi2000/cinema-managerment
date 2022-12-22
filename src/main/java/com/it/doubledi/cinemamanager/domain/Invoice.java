package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends AuditableDomain {
    private String id;
    private String userId;
    private String customerId;
    private Instant paymentTime;
    private Double total;
    private Boolean deleted;
    private String locationId;

    private List<Item> items;
    private List<Ticket> tickets;

    public Invoice(String userId) {
        this.id = IdUtils.nextId();
        this.userId = userId;
        this.paymentTime = Instant.now();
        this.deleted = Boolean.FALSE;
        this.items = new ArrayList<>();
    }

    public void calculatorTotal() {
        this.total = this.items.stream().reduce(0d, (t, item) -> t + item.getQuantity() * item.getPrice(), Double::sum);
    }

    public double calTotalDrinkRevenue() {
        return this.items.stream().filter(i -> Objects.equals(i.getType(), ItemType.DRINK)).map(i -> i.getPrice() * i.getQuantity()).reduce(0d, Double::sum);
    }

    public double calTotalTicketRevenue() {
        return this.items.stream().filter(i -> Objects.equals(i.getType(), ItemType.TICKET)).map(Item::getPrice).reduce(0d, Double::sum);
    }


    public void addItem(Ticket ticket) {
        if (CollectionUtils.isEmpty(this.items)) {
            this.items = new ArrayList<>();
        }
        Item item = new Item(ticket, this.id);
        this.items.add(item);
    }

    public void addItem(Drink drink, int quantity) {
        if (CollectionUtils.isEmpty(this.items)) {
            this.items = new ArrayList<>();
        }
        Item item = new Item(drink, quantity, this.id);
        this.items.add(item);
    }

    public void enrichItem(List<Item> items) {
        if (!CollectionUtils.isEmpty(items)) {
            this.items = items;
        }
        this.items = new ArrayList<>();
    }
}
