package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.util.PageableMapperUtil;
import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.UserService;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.domain.command.UserCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.UserUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.UserSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.UserRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserLocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.UserEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserLocationEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.UserStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AutoMapper autoMapper;
    private final UserEntityRepository userEntityRepository;
    private final AutoMapperQuery autoMapperQuery;
    private final UserLocationEntityRepository userLocationEntityRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User create(UserCreateRequest request) {
        UserCreateCmd cmd = this.autoMapper.from(request);
        //check code exist
        Optional<UserEntity> userFindByCode = this.userEntityRepository.findByCode(cmd.getEmployeeCode());
        if (userFindByCode.isPresent()) {
            throw new ResponseException(BadRequestError.EMPLOYEE_CODE_EXISTED);
        }
        //checkout username
        Optional<UserEntity> userFindByUsername = this.userEntityRepository.findUserByUsername(cmd.getUsername());
        if (userFindByUsername.isPresent()) {
            throw new ResponseException(BadRequestError.USERNAME_EXISTED);
        }
        //check email
        Optional<UserEntity> userFindByEmail = this.userEntityRepository.findByEmail(cmd.getEmail());
        if (userFindByEmail.isPresent()) {
            throw new ResponseException(BadRequestError.EMAIL_EXISTED);
        }

        Optional<UserEntity> userFindByPhoneNumber = this.userEntityRepository.findByPhoneNumber(cmd.getPhoneNumber());
        if (userFindByPhoneNumber.isPresent()) {
            throw new ResponseException(BadRequestError.PHONE_NUMBER_EXISTED);
        }
        User user = new User(cmd);


//        String encodedPassword = this.passwordEncoder.encode(cmd.getPassword());
//        cmd.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User update(String id, UserUpdateRequest request) {
        UserUpdateCmd cmd = this.autoMapper.from(request);
        User user = this.userRepository.getById(id);
        if (!Objects.equals(cmd.getEmail(), user.getEmail())) {
            Optional<UserEntity> userFindByEmail = this.userEntityRepository.findByEmail(cmd.getEmail());
            if (userFindByEmail.isPresent()) {
                throw new ResponseException(BadRequestError.EMAIL_EXISTED);
            }
        }

        if (!Objects.equals(cmd.getPhoneNumber(), user.getPhoneNumber())) {
            Optional<UserEntity> userFindByPhoneNumber = this.userEntityRepository.findByPhoneNumber(cmd.getPhoneNumber());
            if (userFindByPhoneNumber.isPresent()) {
                throw new ResponseException(BadRequestError.PHONE_NUMBER_EXISTED);
            }
        }
        user.deleteAllLocation();
        user.update(cmd);
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return this.userRepository.getById(id);
    }

    @Override
    public PageDTO<User> search(UserSearchRequest request) {
        UserSearchQuery query = this.autoMapperQuery.toQuery(request);
        List<String> userIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(query.getLocationIds())) {
            List<UserLocationEntity> userLocationEntities = this.userLocationEntityRepository.findByLocationIds(query.getLocationIds());
            List<String> userTmp = userLocationEntities.stream().map(UserLocationEntity::getUserId).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userTmp)) {
                userIds = userTmp;
            } else {
                return PageDTO.empty();
            }
        }
        query.setUserIds(userIds);
        Long count = this.userEntityRepository.count(query);
        if (count == 0) {
            return PageDTO.empty();
        }

        List<UserEntity> userEntities = this.userEntityRepository.search(query);
        List<User> users = this.userEntityMapper.toDomain(userEntities);
        return new PageDTO<>(users, query.getPageIndex(), query.getPageSize(), count);
    }

    @Override
    public PageDTO<User> autoComplete(UserSearchRequest request) {
        List<String> userIds = null;
        if (!CollectionUtils.isEmpty(request.getLocationIds())) {
            List<UserLocationEntity> userLocationEntities = this.userLocationEntityRepository.findByLocationIds(request.getLocationIds());
            List<String> userTmp = userLocationEntities.stream().map(UserLocationEntity::getUserId).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userTmp)) {
                userIds = userTmp;
            } else {
                return PageDTO.empty();
            }
        }
        Pageable pageable = PageableMapperUtil.toPageable(request);
        Page<UserEntity> userEntityPage = this.userEntityRepository.autoComplete(SqlUtils.encodeKeyword(request.getKeyword()), List.of(UserStatus.ACTIVE), userIds, pageable);
        List<User> users = this.userEntityMapper.toDomain(userEntityPage.getContent());

        return new PageDTO<>(users, pageable.getPageNumber(), pageable.getPageSize(), userEntityPage.getTotalElements());
    }
}
