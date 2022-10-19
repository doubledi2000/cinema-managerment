package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.domain.repository.ChairRepository;
import com.it.doubledi.cinemamanager.domain.repository.RowRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ChairEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.ChairEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.ChairEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RowEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RowDomainRepositoryImpl extends AbstractDomainRepository<Row, RowEntity, String> implements RowRepository {

    private final RowEntityRepository rowEntityRepository;
    private final RowEntityMapper rowEntityMapper;
    private final ChairRepository chairRepository;
    private final ChairEntityRepository chairEntityRepository;
    private final ChairEntityMapper chairEntityMapper;

    public RowDomainRepositoryImpl(RowEntityRepository rowEntityRepository,
                                   RowEntityMapper rowEntityMapper,
                                   ChairRepository chairRepository,
                                   ChairEntityRepository chairEntityRepository,
                                   ChairEntityMapper chairEntityMapper) {
        super(rowEntityRepository, rowEntityMapper);
        this.rowEntityRepository = rowEntityRepository;
        this.rowEntityMapper = rowEntityMapper;
        this.chairRepository = chairRepository;
        this.chairEntityRepository = chairEntityRepository;
        this.chairEntityMapper = chairEntityMapper;
    }

    @Override
    public Row getById(String id) {
        return this.findById(id).orElseThrow(()-> new ResponseException(NotFoundError.ROOM_NOT_FOUND));
    }

    @Override
    public Row save(Row domain) {
        if(!CollectionUtils.isEmpty(domain.getChairs())) {
            this.chairRepository.saveALl(domain.getChairs());
        }
        return super.save(domain);
    }

    @Override
    public List<Row> saveALl(List<Row> domains) {
        List<Chair> chairs = new ArrayList<>();

        super.saveALl(domains);
        domains.forEach(d -> {
            if(!CollectionUtils.isEmpty(d.getChairs())) {
                chairs.addAll(d.getChairs());
            }
        });
        if(!CollectionUtils.isEmpty(chairs)) {
            this.chairRepository.saveALl(chairs);
        }
        return domains;
    }

    @Override
    protected List<Row> enrichList(List<Row> rows) {
        List<ChairEntity> chairEntities = chairEntityRepository.getAllChairByRowIds(
                rows.stream()
                        .map(Row::getId)
                        .distinct()
                        .collect(Collectors.toList())) ;
        List<Chair> chairs = chairEntityMapper.toDomain(chairEntities);
        for (Row row : rows) {
            List<Chair> chairTmp = chairs.stream().filter(c -> Objects.equals(c.getRowId(), row.getId())).collect(Collectors.toList());
            row.enrichChairs(chairTmp);
        }
        return rows;
    }
}
