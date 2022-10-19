package com.it.doubledi.cinemamanager.infrastructure.persistence.domainrepository;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager._common.web.AbstractDomainRepository;
import com.it.doubledi.cinemamanager.domain.Chair;
import com.it.doubledi.cinemamanager.domain.Row;
import com.it.doubledi.cinemamanager.domain.Seat;
import com.it.doubledi.cinemamanager.domain.repository.ChairRepository;
import com.it.doubledi.cinemamanager.domain.repository.RowRepository;
import com.it.doubledi.cinemamanager.domain.repository.SeatRepository;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RowEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.mapper.RowEntityMapper;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.RowEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RowDomainRepositoryImpl extends AbstractDomainRepository<Row, RowEntity, String> implements RowRepository {

    private final RowEntityRepository rowEntityRepository;
    private final RowEntityMapper rowEntityMapper;
    private final SeatRepository chairRepository;

    public RowDomainRepositoryImpl(RowEntityRepository rowEntityRepository,
                                   RowEntityMapper rowEntityMapper,
                                   SeatRepository chairRepository) {
        super(rowEntityRepository, rowEntityMapper);
        this.rowEntityRepository = rowEntityRepository;
        this.rowEntityMapper = rowEntityMapper;
        this.chairRepository = chairRepository;
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
        List<Seat> chairs = new ArrayList<>();

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
}
