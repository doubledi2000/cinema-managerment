package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.enums.Property;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePermission extends AuditableDomain {
    private String id;
    private Property property;
    private String roleId;
    private String permissionId;
    private Boolean deleted;

    public RolePermission(String roleId, String permissionId) {
        this.id = IdUtils.nextId();
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.deleted = Boolean.FALSE;
    }

    public void delete(){
        this.deleted = Boolean.TRUE;
    }

    public void undelete(){
        this.deleted = Boolean.FALSE;
    }


}
