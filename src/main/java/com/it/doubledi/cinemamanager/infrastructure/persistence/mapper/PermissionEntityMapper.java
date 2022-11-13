package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.Permission;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionEntityMapper extends EntityMapper<Permission, PermissionEntity> {
}
