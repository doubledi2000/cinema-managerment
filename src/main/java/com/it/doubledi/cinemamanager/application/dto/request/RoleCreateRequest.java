package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.RoleLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleCreateRequest extends Request {
    private String id;
    private String code;
    private String name;
    private String description;
    private Boolean isRoot;
    private RoleLevel roleLevel;
}
