package com.it.doubledi.cinemamanager.infrastructure.persistence.entity;

import com.it.doubledi.cinemamanager._common.model.entity.AuditableEntity;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "item", indexes = {
        @Index(name = "item_invoice_id_idx", columnList = "invoice_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String id;

    @Column(name = "type", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    private ItemType type;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "item_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "invoice_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String invoiceId;
}
