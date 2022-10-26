package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Film;
import com.it.doubledi.cinemamanager.domain.FilmType;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmTypeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FilmTypeEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.TypeOfFilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FilmTypeEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.TypeOfFilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmDomainRepositoryImpl extends AbstractDomainRepository<Film, FilmEntity, String> implements FilmRepository {
    private final FilmEntityRepository filmEntityRepository;
    private final FilmEntityMapper filmEntityMapper;
    private final TypeOfFilmEntityRepository typeOfFilmEntityRepository;
    private final TypeOfFilmEntityMapper typeOfFilmEntityMapper;
    private final FilmTypeEntityRepository filmTypeEntityRepository;
    private final FilmTypeEntityMapper filmTypeEntityMapper;

    public FilmDomainRepositoryImpl(FilmEntityRepository filmEntityRepository,
                                    FilmEntityMapper filmEntityMapper,
                                    TypeOfFilmEntityRepository typeOfFilmEntityRepository,
                                    TypeOfFilmEntityMapper typeOfFilmEntityMapper,
                                    FilmTypeEntityRepository filmTypeEntityRepository,
                                    FilmTypeEntityMapper filmTypeEntityMapper) {
        super(filmEntityRepository, filmEntityMapper);
        this.filmEntityRepository = filmEntityRepository;
        this.filmEntityMapper = filmEntityMapper;
        this.typeOfFilmEntityRepository = typeOfFilmEntityRepository;
        this.typeOfFilmEntityMapper = typeOfFilmEntityMapper;
        this.filmTypeEntityRepository = filmTypeEntityRepository;
        this.filmTypeEntityMapper = filmTypeEntityMapper;
    }

    @Override
    public Film getById(String id) {
        return this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.FILM_NOT_FOUND));
    }

    @Override
    @Transactional
    public Film save(Film domain) {
        this.saveALl(List.of(domain));
        return domain;
    }

    @Override
    @Transactional
    public List<Film> saveALl(List<Film> domains) {
        List<FilmType> filmTypes = new ArrayList<>();
        domains.forEach(d -> {
            if(!CollectionUtils.isEmpty(d.getFilmTypes())) {
                filmTypes.addAll(d.getFilmTypes());
            }
        });
        List<FilmTypeEntity> filmTypeEntities = this.filmTypeEntityMapper.toEntity(filmTypes);
        this.filmTypeEntityRepository.saveAll(filmTypeEntities);
        return super.saveALl(domains);
    }

    @Override
    protected List<Film> enrichList(List<Film> films) {
        List<String> filmIds = films.stream().map(Film::getId).collect(Collectors.toList());
        List<FilmTypeEntity> filmTypeEntities = this.filmTypeEntityRepository.getAllFilmTypeByFilmIds(filmIds);
        List<String> typeOfFilmIds =  filmTypeEntities.stream().map(FilmTypeEntity::getTypeId).collect(Collectors.toList());
        List<TypeOfFilmEntity> typeOfFilmEntities = this.typeOfFilmEntityRepository.findAllTypeByIds(typeOfFilmIds);
        List<TypeOfFilm> typeOfFilms = this.typeOfFilmEntityMapper.toDomain(typeOfFilmEntities);
        List<FilmType> filmTypes = this.filmTypeEntityMapper.toDomain(filmTypeEntities);
        for (FilmType filmType : filmTypes) {
            Optional<TypeOfFilm> typeOfFilm = typeOfFilms.stream().filter(t -> Objects.equals(t.getId(), filmType.getTypeId())).findFirst();
            typeOfFilm.ifPresent(filmType::enrichType);
        }
        for (Film film : films) {
            List<FilmType> filmTypeTpm = filmTypes.stream().filter(f -> Objects.equals(f.getFilmId(), film.getId())).collect(Collectors.toList());
            film.enrichFilmType(filmTypeTpm);
        }
        return films;
    }
}
