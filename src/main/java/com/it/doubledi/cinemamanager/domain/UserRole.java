package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
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
public class UserRole extends AuditableDomain {
    private String id;
    private String userId;
    private String roleId;
    private Boolean deleted;

    private String roleName;

    public UserRole(String userId, String roleId) {
        this.id = IdUtils.nextId();
        this.userId = userId;
        this.roleId = roleId;
        this.deleted = Boolean.FALSE;
    }

    public void delete(){
        this.deleted = Boolean.TRUE;
    }

    public void undelete(){
        this.deleted = Boolean.FALSE;
    }

    public void enrichRole(Role role) {
        this.roleName = role.getName();
    }
}
