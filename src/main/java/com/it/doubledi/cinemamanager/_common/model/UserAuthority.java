package com.it.doubledi.cinemamanager._common.model;

import com.it.doubledi.cinemamanager._common.model.enums.UserLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthority {
    private String userId;
    private Boolean isRoot;
    private UserLevel userLevel;
    private List<String> locationIds;
    private List<String> grantedPermissions;
}
