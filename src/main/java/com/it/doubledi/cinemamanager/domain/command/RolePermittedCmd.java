package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RolePermittedCmd {
    List<String> permissionIds;
}
