package com.it.doubledi.cinemamanager.domain;

import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.model.enums.Gender;
import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.domain.command.UserCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.UserUpdateCmd;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditableDomain {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dayOfBirth;
    private Gender gender;
    private Boolean deleted;
    private String employeeCode;
    private String title;
    private String departmentName;
    private String description;
    private UserStatus status;
    private UserLevel userLevel;
    private String avatarFileId;

    //enrich
    private List<String> locationIds;
    private List<UserLocation> locations;
    private List<String> roleIds;
    private List<UserRole> roles;

    public User(UserCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.username = cmd.getUsername();
        this.fullName = cmd.getFullName();
        this.email = cmd.getEmail();
        this.phoneNumber = cmd.getPhoneNumber();
        this.dayOfBirth = cmd.getDayOfBirth();
        this.gender = cmd.getGender();
        this.deleted = Boolean.FALSE;
        this.employeeCode = cmd.getEmployeeCode();
        this.title = cmd.getTitle();
        this.departmentName = cmd.getDepartmentName();
        this.description = cmd.getDescription();
        this.status = UserStatus.ACTIVE;
        for (String locationId : cmd.getLocationIds()) {
            this.addUserLocation(new UserLocation(this.id, locationId));
        }
    }

    public void update(UserUpdateCmd cmd) {
        this.fullName = cmd.getFullName();
        this.email = cmd.getEmail();
        this.phoneNumber = cmd.getPhoneNumber();
        this.dayOfBirth = cmd.getDayOfBirth();
        this.gender = cmd.getGender();
        this.title = cmd.getTitle();
        this.departmentName = cmd.getDepartmentName();
        this.updateRole(cmd);
        this.updateLocation(cmd);
    }

    public void enrichUserLocation(List<UserLocation> userLocations) {
        if (CollectionUtils.isEmpty(userLocations)) {
            this.locations = new ArrayList<>();
        } else {
            this.locations = userLocations;
        }
    }

    public void enrichLocationIds(List<String> locationIds) {
        if (CollectionUtils.isEmpty(locationIds)) {
            this.locationIds = new ArrayList<>();
        } else {
            this.locationIds = locationIds;
        }
    }

    public void enrichRoleIds(List<String> roleIds) {
        if (!CollectionUtils.isEmpty(roleIds)) {
            this.roleIds = roleIds;
        } else {
            this.roleIds = new ArrayList<>();
        }
    }

    public void enrichUserRole(List<UserRole> userRoles) {
        if (!CollectionUtils.isEmpty(userRoles)) {
            this.roles = userRoles;
        } else {
            this.roles = new ArrayList<>();
        }
    }

    public void addUserLocation(UserLocation userLocation) {
        if (CollectionUtils.isEmpty(this.locations)) {
            this.locations = new ArrayList<>();
        }
        this.locations.add(userLocation);
    }

    public void deleteAllLocation() {
        this.getLocations().forEach(UserLocation::delete);
    }

    public void updateRole(UserUpdateCmd cmd) {
        if (!CollectionUtils.isEmpty(this.roles)) {
            this.roles.forEach(UserRole::delete);
        } else {
            this.roles = new ArrayList<>();
        }

        cmd.getRoleIds().forEach(roleId -> {
            Optional<UserRole> userRoleOptional = this.roles.stream().filter(r -> Objects.equals(r.getRoleId(), roleId)).findFirst();
            if (userRoleOptional.isPresent()) {
                userRoleOptional.get().undelete();
            } else {
                this.addRole(new UserRole(this.id, roleId));
            }
        });
    }

    public void addRole(UserRole userRole) {
        if (!CollectionUtils.isEmpty(this.roles)) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(userRole);
    }

    public void updateLocation(UserUpdateCmd cmd) {
        if (!CollectionUtils.isEmpty(this.locations)) {
            this.locations.forEach(UserLocation::delete);
        } else {
            this.locations = new ArrayList<>();
        }

        cmd.getLocationIds().forEach(locationId -> {
            Optional<UserLocation> userLocationOptional = this.locations.stream().filter(r -> Objects.equals(r.getBuildingId(), locationId)).findFirst();
            if (userLocationOptional.isPresent()) {
                userLocationOptional.get().undelete();
            } else {
                this.addUserLocation(new UserLocation(this.id, locationId));
            }
        });
    }

}
