package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleUpdateCmd {
    private String name;
    private String description;
    private Boolean isRoot;
    private RoleLevel roleLevel;
}
