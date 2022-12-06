package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.model.mapper.EntityMapper;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Drink;
import com.it.doubledi.cinemamanager.domain.File;
import com.it.doubledi.cinemamanager.domain.repository.DrinkRepository;
import com.it.doubledi.cinemamanager.domain.repository.FileRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FileEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.FileEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.FileEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DrinkDomainRepositoryImpl extends AbstractDomainRepository<Drink, DrinkEntity, String> implements DrinkRepository {
    private final FileRepository fileRepository;
    private final FileEntityRepository fileEntityRepository;
    private final FileEntityMapper fileEntityMapper;

    public DrinkDomainRepositoryImpl(JpaRepository<DrinkEntity, String> jpaRepository,
                                     EntityMapper<Drink, DrinkEntity> entityMapper,
                                     FileRepository fileRepository,
                                     FileEntityRepository fileEntityRepository,
                                     FileEntityMapper fileEntityMapper) {
        super(jpaRepository, entityMapper);
        this.fileRepository = fileRepository;
        this.fileEntityRepository = fileEntityRepository;
        this.fileEntityMapper = fileEntityMapper;
    }

    @Override
    public Drink getById(String id) {
        Drink drink = this.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.DRINK_NOT_FOUND));
        return drink;
    }

    @Override
    public List<Drink> enrichList(List<Drink> drinks) {
        List<String> fileIds = drinks.stream().map(Drink::getFileId).filter(StringUtils::hasLength).collect(Collectors.toList());
        List<FileEntity> fileEntities = this.fileEntityRepository.findByIds(fileIds);
        List<File> files = this.fileEntityMapper.toDomain(fileEntities);
        drinks.forEach(d -> {
            Optional<File> fileOptional = files.stream().filter(f -> Objects.equals(f.getId(), d.getFileId())).findFirst();
            fileOptional.ifPresent(d::enrichFile);
        });
        return drinks;
    }
}
