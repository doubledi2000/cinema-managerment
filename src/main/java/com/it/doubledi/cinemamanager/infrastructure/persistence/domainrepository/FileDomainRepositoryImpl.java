package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.File;
import com.it.doubledi.cinemamanager.domain.repository.FileRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FileEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FileEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FileEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FileDomainRepositoryImpl extends AbstractDomainRepository<File, FileEntity, String> implements FileRepository {
    public FileDomainRepositoryImpl(JpaRepository<FileEntity, String> jpaRepository, EntityMapper<File, FileEntity> entityMapper) {
        super(jpaRepository, entityMapper);
    }

    @Override
    public File getById(String id) {
        return entityMapper.toDomain(this.jpaRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.FILM_NOT_FOUND)));
    }
}
