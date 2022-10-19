package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.util.PageableMapperUtil;
import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmSearchRequest;
import com.it.doubledi.cinemamanager.application.dto.request.TypeOfFilmUpdateRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapperQuery;
import com.it.doubledi.cinemamanager.application.service.TypeOfFilmService;
import com.it.doubledi.cinemamanager.domain.TypeOfFilm;
import com.it.doubledi.cinemamanager.domain.command.TypeOfFilmCreateCmd;
import com.it.doubledi.cinemamanager.domain.command.TypeOfFilmUpdateCmd;
import com.it.doubledi.cinemamanager.domain.query.TypeOfFilmSearchQuery;
import com.it.doubledi.cinemamanager.domain.repository.TypeOfFilmRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.TypeOfFilmEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.TypeOfFilmEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TypeOfFilmStatus;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.BadRequestError;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeOfFilmServiceImpl implements TypeOfFilmService {
    private final TypeOfFilmRepository typeOfFilmRepository;
    private final TypeOfFilmEntityRepository typeOfFilmEntityRepository;
    private final TypeOfFilmEntityMapper typeOfFilmEntityMapper;
    private final AutoMapper autoMapper;
    private final AutoMapperQuery autoMapperQuery;
    @Override
    public TypeOfFilm create(TypeOfFilmCreateRequest request) {
        TypeOfFilmCreateCmd cmd = autoMapper.from(request);
        Optional<TypeOfFilmEntity> typeOfFilmEntityOptional = this.typeOfFilmEntityRepository.findByCode(cmd.getCode());
        if(typeOfFilmEntityOptional.isPresent()) {
            throw new ResponseException(BadRequestError.TYPE_OF_FILM_CODE_ALREADY_EXISTED);
        }
        TypeOfFilm  typeOfFilm = new TypeOfFilm(cmd);
        return this.typeOfFilmRepository.save(typeOfFilm);
    }

    @Override
    public TypeOfFilm update(String id, TypeOfFilmUpdateRequest request) {
        TypeOfFilmUpdateCmd cmd = autoMapper.from(request);
        TypeOfFilm typeOfFilm = this.typeOfFilmRepository.getById(id);
        typeOfFilm.update(cmd);
        return typeOfFilm;
    }

    @Override
    public TypeOfFilm getById(String id) {
        return typeOfFilmRepository.getById(id);
    }

    @Override
    public PageDTO<TypeOfFilm> search(TypeOfFilmSearchRequest request) {
        TypeOfFilmSearchQuery query = this.autoMapperQuery.toQuery(request);
        Long total = this.typeOfFilmEntityRepository.count(query);
        if (total == 0) {
            return PageDTO.empty();
        }
        List<TypeOfFilmEntity> typeOfFilmEntities = this.typeOfFilmEntityRepository.search(query);
        List<TypeOfFilm> typeOfFilms = typeOfFilmEntityMapper.toDomain(typeOfFilmEntities);
        return new PageDTO<>(typeOfFilms, query.getPageIndex(), query.getPageSize(), total);
    }

    @Override
    public PageDTO<TypeOfFilm> autoComplete(TypeOfFilmSearchRequest request) {
        Pageable pageable = PageableMapperUtil.toPageable(request);
        Page<TypeOfFilmEntity> typeOfFilmEntitiesPage = this.typeOfFilmEntityRepository.autoComplete(SqlUtils.encodeKeyword(request.getKeyword()), List.of(TypeOfFilmStatus.ACTIVE), pageable);
        List<TypeOfFilm> typeOfFilms = this.typeOfFilmEntityMapper.toDomain(typeOfFilmEntitiesPage.getContent());
        return new PageDTO<>(typeOfFilms,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                typeOfFilmEntitiesPage.getTotalPages());
    }
}
