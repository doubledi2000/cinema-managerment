package com.it.doubledi.cinemamanager.infrastructure.persistence.mapper;

import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager.domain.UserRole;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleEntityMapper extends EntityMapper<UserRole, UserRoleEntity> {
}
