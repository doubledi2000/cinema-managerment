package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.*;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.*;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.*;
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
    private final FilmProducerEntityRepository filmProducerEntityRepository;
    private final FilmProducerEntityMapper filmProducerEntityMapper;
    private final ProducerEntityRepository producerEntityRepository;
    private final ProducerEntityMapper producerEntityMapper;

    public FilmDomainRepositoryImpl(FilmEntityRepository filmEntityRepository,
                                    FilmEntityMapper filmEntityMapper,
                                    TypeOfFilmEntityRepository typeOfFilmEntityRepository,
                                    TypeOfFilmEntityMapper typeOfFilmEntityMapper,
                                    FilmTypeEntityRepository filmTypeEntityRepository,
                                    FilmTypeEntityMapper filmTypeEntityMapper,
                                    FilmProducerEntityRepository filmProducerEntityRepository,
                                    FilmProducerEntityMapper filmProducerEntityMapper,
                                    ProducerEntityRepository producerEntityRepository,
                                    ProducerEntityMapper producerEntityMapper) {
        super(filmEntityRepository, filmEntityMapper);
        this.filmEntityRepository = filmEntityRepository;
        this.filmEntityMapper = filmEntityMapper;
        this.typeOfFilmEntityRepository = typeOfFilmEntityRepository;
        this.typeOfFilmEntityMapper = typeOfFilmEntityMapper;
        this.filmTypeEntityRepository = filmTypeEntityRepository;
        this.filmTypeEntityMapper = filmTypeEntityMapper;
        this.filmProducerEntityRepository = filmProducerEntityRepository;
        this.filmProducerEntityMapper = filmProducerEntityMapper;
        this.producerEntityRepository = producerEntityRepository;
        this.producerEntityMapper = producerEntityMapper;
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
        List<FilmProducer> filmProducers = new ArrayList<>();
        domains.forEach(d -> {
            if (!CollectionUtils.isEmpty(d.getFilmTypes())) {
                filmTypes.addAll(d.getFilmTypes());
            }
            if (!CollectionUtils.isEmpty(d.getFilmProducers())) {
                filmProducers.addAll(d.getFilmProducers());
            }
        });
        List<FilmTypeEntity> filmTypeEntities = this.filmTypeEntityMapper.toEntity(filmTypes);
        this.filmTypeEntityRepository.saveAll(filmTypeEntities);
        List<FilmProducerEntity> filmProducerEntities = this.filmProducerEntityMapper.toEntity(filmProducers);
        this.filmProducerEntityRepository.saveAll(filmProducerEntities);
        return super.saveALl(domains);
    }

    @Override
    public List<Film> enrichList(List<Film> films) {
        List<String> filmIds = films.stream().map(Film::getId).collect(Collectors.toList());

        List<FilmTypeEntity> filmTypeEntities = this.filmTypeEntityRepository.getAllFilmTypeByFilmIds(filmIds);
        List<String> typeOfFilmIds = filmTypeEntities.stream().map(FilmTypeEntity::getTypeId).collect(Collectors.toList());
        List<TypeOfFilmEntity> typeOfFilmEntities = this.typeOfFilmEntityRepository.findAllTypeByIds(typeOfFilmIds);
        List<TypeOfFilm> typeOfFilms = this.typeOfFilmEntityMapper.toDomain(typeOfFilmEntities);

        List<FilmProducerEntity> filmProducerEntities = this.filmProducerEntityRepository.findAllByFilmIds(filmIds);
        List<FilmProducer> filmProducers = this.filmProducerEntityMapper.toDomain(filmProducerEntities);
        List<ProducerEntity> producerEntities = this.producerEntityRepository.findByIds(filmProducers.stream().map(FilmProducer::getProducerId).collect(Collectors.toList()));
        List<Producer> producers = this.producerEntityMapper.toDomain(producerEntities);

        List<FilmType> filmTypes = this.filmTypeEntityMapper.toDomain(filmTypeEntities);
        for (FilmType filmType : filmTypes) {
            Optional<TypeOfFilm> typeOfFilm = typeOfFilms.stream().filter(t -> Objects.equals(t.getId(), filmType.getTypeId())).findFirst();
            typeOfFilm.ifPresent(filmType::enrichType);
        }
        for (FilmProducer filmProducer : filmProducers) {
            Optional<Producer> producer = producers.stream().filter(p -> Objects.equals(p.getId(), filmProducer.getProducerId())).findFirst();
            producer.ifPresent(filmProducer::enrichProducer);
        }
        for (Film film : films) {
            List<FilmType> filmTypeTpm = filmTypes.stream().filter(f -> Objects.equals(f.getFilmId(), film.getId())).collect(Collectors.toList());
            List<FilmProducer> filmProducerTmp = filmProducers.stream().filter(p -> Objects.equals(p.getFilmId(), film.getId())).collect(Collectors.toList());
            film.enrichProducer(filmProducerTmp);
            film.enrichFilmType(filmTypeTpm);
        }
        return films;
    }
}
