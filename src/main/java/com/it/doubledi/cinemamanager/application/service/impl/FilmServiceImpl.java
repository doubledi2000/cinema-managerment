package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.FilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.FilmSearchRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.FilmService;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.domain.FilmType;
import com.it.doubledi.cinemamanager.domain.command.FilmCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.TypeOfFilmEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final AutoMapper autoMapper;
    private final TypeOfFilmEntityRepository typeOfFilmEntityRepository;


    @Override
    public Film create(FilmCreateRequest request) {
        FilmCreateCmd cmd = this.autoMapper.from(request);
        Film film = new Film(cmd);
        List<FilmType> filmTypes = new ArrayList<>();
        List<TypeOfFilmEntity> typeOfFilmEntities = this.typeOfFilmEntityRepository.findAllTypeByIds(request.getFilmTypeIds());
        request.getFilmTypeIds().retainAll(typeOfFilmEntities.stream().map(TypeOfFilmEntity::getId).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(request.getFilmTypeIds())) {
            for (String filmTypeId : request.getFilmTypeIds()) {
                FilmType filmType = new FilmType(film.getId(), filmTypeId);
                filmTypes.add(filmType);
            }
        }
        film.enrichFilmType(filmTypes);
        this.filmRepository.save(film);
        return film;
    }

    @Override
    public Film update(String id, FilmCreateRequest request) {
        return null;
    }

    @Override
    public Film getById(String id) {
        return this.filmRepository.getById(id);
    }

    @Override
    public PageDTO<Film> search(FilmSearchRequest request) {
        return null;
    }

    @Override
    public PageDTO<Film> autoComplete(FilmSearchRequest request) {
        return null;
    }
}
