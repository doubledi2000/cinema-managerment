package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.InvoiceCreateCmd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    private void getListItem(InvoiceCreateCmd cmd, List<Drink> drinks) {
        cmd.getItems().forEach(i -> {
            Optional<Drink> drinkOptional = drinks.stream().filter(d -> Objects.equals(d.getId(), i.getItemId())).findFirst();
            if (drinkOptional.isPresent()) {
                Item item = new Item(drinkOptional.get(), i.getQuantity(), this.id);
                this.addItem(item);
            }
        });
    }

    private void addItem(Item item) {
        if (CollectionUtils.isEmpty(this.items)) {
            this.items = new ArrayList<>();
        }
        items.add(item);
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
