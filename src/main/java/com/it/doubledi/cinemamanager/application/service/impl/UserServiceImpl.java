package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.UserService;
import com.it.doubledi.cinemamanager.domain.User;
import com.it.doubledi.cinemamanager.domain.command.UserCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.UserUpdateCmd;
import com.it.doubledi.cinemamanager.domain.repository.UserRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AutoMapper autoMapper;
    private final UserEntityRepository userEntityRepository;

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

        User user = new User(cmd);
//        String encodedPassword = this.passwordEncoder.encode(cmd.getPassword());
//        cmd.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User update(String id, UserUpdateRequest request) {
        UserUpdateCmd cmd = this.autoMapper.from(request);
        User user = this.userRepository.getById(id);
        user.update(cmd);
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public PageDTO<User> search() {
        return null;
    }

    @Override
    public PageDTO<User> autoComplete() {
        return null;
    }
}
