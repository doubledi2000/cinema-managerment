package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.ProducerCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.ProducerUpdateCmd;
import lombok.*;

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
    private String nationally;
    private Long version;

    public Producer(ProducerCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.deleted = Boolean.FALSE;
        this.representative = cmd.getRepresentative();
        this.nationally = cmd.getNationally();
    }

    public void update(ProducerUpdateCmd cmd) {
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.representative = cmd.getRepresentative();
        this.nationally = cmd.getNationally();
    }

}
