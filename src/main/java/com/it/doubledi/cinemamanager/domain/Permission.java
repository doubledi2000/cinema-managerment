package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.enums.Scope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends AuditableDomain {
    private String id;
    private Scope scope;
    private String resourceCode;
    private String name;
    private Boolean deleted;
    private int priority;

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void undelete() {
        this.deleted = Boolean.FALSE;
    }
}
