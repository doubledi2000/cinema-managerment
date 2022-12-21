package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.Showtime;

import java.util.List;

public interface ShowtimeRepository extends DomainRepository<Showtime, String> {

    List<Showtime> enrichList(List<Showtime> domains);
}
