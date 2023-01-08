package com.it.doubledi.cinemamanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.doubledi.cinemamanager._common.model.domain.AuditableDomain;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation extends AuditableDomain {
    private String id;
    private String buildingId;
    private String userId;
    private Boolean deleted;

    private User user;
    private Location location;

    public UserLocation(String userId, String buildingId) {
        this.id = IdUtils.nextId();
        this.userId = userId;
        this.buildingId = buildingId;
        this.deleted = Boolean.FALSE;
    }


    public void enrichUser(User user) {
        this.user = user;
    }

    public void enrichLocation(Location location) {
        this.location = location;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void undelete() {
        this.deleted = Boolean.FALSE;
    }
}
