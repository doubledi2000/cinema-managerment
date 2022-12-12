package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
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
