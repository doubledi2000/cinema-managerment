package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.Film;

import java.util.List;

public interface FilmRepository extends DomainRepository<Film, String> {
    List<Film> enrichList(List<Film> films);
}
