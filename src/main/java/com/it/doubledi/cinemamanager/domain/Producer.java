package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ProducerCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ProducerUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ProducerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producer extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private Boolean deleted;
    private String representative;
    private ProducerStatus status;
    private String nationally;
    private Long version;

    public Producer(ProducerCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.deleted = Boolean.FALSE;
        this.status = ProducerStatus.INACTIVE;
        this.representative = cmd.getRepresentative();
        this.nationally = cmd.getNationally();
    }

    public void update(ProducerUpdateCmd cmd) {
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.representative = cmd.getRepresentative();
        this.nationally = cmd.getNationally();
    }

    public void active() {
        this.status = ProducerStatus.ACTIVE;
    }

    public void inactive() {
        this.status = ProducerStatus.INACTIVE;
    }

}
