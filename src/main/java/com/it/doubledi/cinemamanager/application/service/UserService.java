package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.UserCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.UserUpdateRequest;
import com.it.doubledi.cinemamanager.domain.User;

public interface UserService {
    User create(UserCreateRequest request);

    User update(String id, UserUpdateRequest request);

    User findById(String id);

    PageDTO<User> search();

    PageDTO<User> autoComplete();
}
