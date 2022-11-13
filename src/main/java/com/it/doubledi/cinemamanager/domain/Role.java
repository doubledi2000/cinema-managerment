package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.RoleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RoleUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleLevel;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private Boolean isRoot;
    private Boolean deleted;
    private RoleStatus status;
    private RoleLevel roleLevel;

    private List<RolePermission> permissions;
    private List<String> permissionIds;

    public Role(RoleCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.isRoot = cmd.getIsRoot();
        this.deleted = Boolean.FALSE;
        this.status = RoleStatus.ACTIVE;
    }

    public void enrichPermissionIds(List<String> permissionIds) {
        if (!CollectionUtils.isEmpty(permissionIds)) {
            this.permissionIds = permissionIds;
        } else {
            this.permissionIds = new ArrayList<>();
        }
    }

    public void update(RoleUpdateCmd cmd) {
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.isRoot = cmd.getIsRoot();
    }
}
