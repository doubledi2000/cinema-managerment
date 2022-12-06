package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager.domain.command.DrinkUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.DrinkStatus;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drink extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private Double price;
    private String locationId;
    private DrinkStatus status;
    private String description;
    private String fileId;
    private Boolean deleted;
    private String imagePath;

    public void update(DrinkUpdateCmd cmd) {
        this.name = cmd.getName();
        this.price = cmd.getPrice();
        this.description = cmd.getDescription();
        this.fileId = cmd.getFileId();
    }

    public void active() {
        this.status = DrinkStatus.ACTIVE;
    }

    public void inactive() {
        this.status = DrinkStatus.INACTIVE;
    }

    public void enrichFile(File file) {
        this.imagePath = file.getPath();
    }
}
