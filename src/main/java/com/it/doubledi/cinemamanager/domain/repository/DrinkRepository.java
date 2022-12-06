package com.it.doubledi.cinemamanager.domain.repository;

import com.it.doubledi.cinemamanager._common.web.DomainRepository;
import com.it.doubledi.cinemamanager.domain.Drink;

import java.util.List;

public interface DrinkRepository extends DomainRepository<Drink, String> {
    List<Drink> enrichList(List<Drink> drinks);
}
