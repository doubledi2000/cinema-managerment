package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.persistence.support.SeqRepository;
import com.it.doubledi.cinemamanager._common.util.IdUtils;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.DrinkUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.DrinkService;
import com.it.doubledi.cinemamanager.domain.Drink;
import com.it.doubledi.cinemamanager.domain.Location;
import com.it.doubledi.cinemamanager.domain.command.DrinkCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.DrinkUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.DrinkSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.DrinkRepository;
import com.it.doubledi.cinemamanager.domain.repository.LocationRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.DrinkEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.DrinkEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.DrinkStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DinkServiceImpl implements DrinkService {
    private final SeqRepository seqRepository;
    private final AutoMapper autoMapper;

    private final LocationRepository locationRepository;
    private final DrinkRepository drinkRepository;
    private final AutoMapperQuery autoMapperQuery;
    private final DrinkEntityRepository drinkEntityRepository;
    private final DrinkEntityMapper drinkEntityMapper;

    @Override
    public Drink create(DrinkCreateRequest request) {
        DrinkCreateCmd cmd = this.autoMapper.from(request);
        Location location = this.locationRepository.getById(cmd.getLocationId());
        Drink drink = Drink.builder()
                .id(IdUtils.nextId())
                .name(cmd.getName())
                .code(this.seqRepository.generateDrinkCode())
                .description(cmd.getDescription())
                .locationId(location.getId())
                .status(DrinkStatus.ACTIVE)
                .deleted(Boolean.FALSE)
                .price(cmd.getPrice())
                .fileId(cmd.getFileId())
                .build();
        return this.drinkRepository.save(drink);
    }

    @Override
    public Drink update(String id, DrinkUpdateRequest request) {
        Drink drink = this.drinkRepository.getById(id);
        DrinkUpdateCmd cmd = this.autoMapper.from(request);
        drink.update(cmd);
        return this.drinkRepository.save(drink);
    }

    @Override
    public Drink getById(String id) {
        return this.drinkRepository.getById(id);
    }

    @Override
    public void active(String id) {
        Drink drink = this.getById(id);
        drink.active();
        this.drinkRepository.save(drink);
    }

    @Override
    public void inactive(String id) {
        Drink drink = this.getById(id);
        drink.inactive();
        this.drinkRepository.save(drink);
    }

    @Override
    public PageDTO<Drink> search(DrinkSearchRequest request) {
        DrinkSearchQuery searchQuery = this.autoMapperQuery.toQuery(request);
        Long count = this.drinkEntityRepository.count(searchQuery);

        if (count == 0) {
            return PageDTO.empty();
        }

        List<DrinkEntity> drinkEntities = this.drinkEntityRepository.search(searchQuery);
        List<Drink> drinks = this.drinkEntityMapper.toDomain(drinkEntities);
        this.drinkRepository.enrichList(drinks);
        return new PageDTO<>(drinks, searchQuery.getPageIndex(), searchQuery.getPageSize(), count);
    }

    @Override
    public PageDTO<Drink> autoComplete(DrinkSearchRequest request) {
        return null;
    }
}
