package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.RoleCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.RolePermissionCmd;
import com.it.doubledi.cinemamanager.domain.command.RolePermittedCmd;
import com.it.doubledi.cinemamanager.domain.command.RoleUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleLevel;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private Boolean isRoot;
    private Boolean deleted;
    private RoleStatus status;
    private RoleLevel roleLevel;
    private Long version;

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

    public void enrichRolePermission(List<RolePermission> rolePermissions) {
        this.permissions = rolePermissions;
    }

    public void update(RoleUpdateCmd cmd) {
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.isRoot = cmd.getIsRoot();
    }

    public void updatePermission(RolePermissionCmd cmd, List<Permission> permissions) {
        this.permissions.forEach(RolePermission::delete);
        cmd.getPermissions().forEach(p -> {
            if (!CollectionUtils.isEmpty(p.getScopes())) {
                p.getScopes().forEach(s -> {
                    Optional<Permission> permissionOptional = permissions.stream()
                            .filter(per -> Objects.equals(per.getResourceCode(), p.getResourceCode()) && Objects.equals(s, per.getScope()))
                            .findFirst();

                    permissionOptional.ifPresent(per -> {
                        Optional<RolePermission> oldPermission = this.permissions.stream().filter(oldPer -> Objects.equals(oldPer.getPermissionId(), per.getId())).findFirst();
                        if (oldPermission.isPresent()) {
                            oldPermission.get().undelete();
                        } else {
                            this.addPermission(new RolePermission(this.id, per.getId()));
                        }
                    });
                });
            }
        });
    }

    public void updatePermission(RolePermittedCmd cmd) {
        this.permissions.forEach(RolePermission::delete);
        for (String permissionId : cmd.getPermissionIds()) {
            Optional<RolePermission> rolePermissionOptional = this.permissions.stream()
                    .filter(r -> Objects.equals(r.getPermissionId(), permissionId))
                    .findFirst();
            if (rolePermissionOptional.isPresent()) {
                rolePermissionOptional.get().undelete();
            } else {
                this.addPermission(new RolePermission(this.id, permissionId));
            }
        }
    }

    public void addPermission(RolePermission rolePermission) {
        if (CollectionUtils.isEmpty(this.getPermissions())) {
            this.permissions = new ArrayList<>();
        }
        this.permissions.add(rolePermission);
    }
}
