package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;
import com.it.doubledi.cinemamanager.domain.repository.TypeOfFilmRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.TypeOfFilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.TypeOfFilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;

@Service
public class TypeOfFilmDomainRepositoryImpl extends AbstractDomainRepository<TypeOfFilm, TypeOfFilmEntity, String> implements TypeOfFilmRepository {
    private final TypeOfFilmEntityRepository typeOfFilmEntityRepository;
    private final TypeOfFilmEntityMapper typeOfFilmEntityMapper;

    public TypeOfFilmDomainRepositoryImpl(TypeOfFilmEntityRepository typeOfFilmEntityRepository,
                                          TypeOfFilmEntityMapper typeOfFilmEntityMapper) {
        super(typeOfFilmEntityRepository, typeOfFilmEntityMapper);
        this.typeOfFilmEntityRepository = typeOfFilmEntityRepository;
        this.typeOfFilmEntityMapper = typeOfFilmEntityMapper;
    }

    @Override
    public TypeOfFilm getById(String id) {
        TypeOfFilmEntity typeOfFilmEntity = typeOfFilmEntityRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.TYPE_OF_FILM_NOT_FOUND));
        return typeOfFilmEntityMapper.toDomain(typeOfFilmEntity);
    }
}
