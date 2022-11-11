package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.User;

import java.util.List;

public interface UserRepository extends DomainRepository<User, String> {

    List<User> enrichList(List<User> users);
}
