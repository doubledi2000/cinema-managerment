package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.RolePermission;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RolePermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePermissionEntityMapper extends EntityMapper<RolePermission, RolePermissionEntity> {
}
