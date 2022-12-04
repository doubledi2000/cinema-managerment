package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager.domain.query.ShowtimeConfigSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ShowtimeEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.ShowtimeRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShowtimeRepositoryCustomImpl implements ShowtimeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShowtimeEntity> search(ShowtimeConfigSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select s from ShowtimeEntity s ");
        sql.append(createWhereQuery(searchQuery, value));
        sql.append(createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), ShowtimeEntity.class);
        value.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(ShowtimeConfigSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select count(*) from ShowtimeEntity s ");
        sql.append(createWhereQuery(searchQuery, value));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        value.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(ShowtimeConfigSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE s.deleted = false");
        sql.append(" AND s.premiereDate = :time ");
        values.put("time", searchQuery.getTime());

        if (!CollectionUtils.isEmpty(searchQuery.getStatuses())) {
            sql.append(" AND s.status in :statuses ");
            values.put("statuses", searchQuery.getStatuses());
        }

        if (!CollectionUtils.isEmpty(searchQuery.getRoomIds())) {
            sql.append(" AND s.roomId in :roomIds ");
            values.put("roomIds", searchQuery.getRoomIds());
        }

        if (!CollectionUtils.isEmpty(searchQuery.getFilmIds())) {
            sql.append(" AND s.filmId in :filmIds");
            values.put("filmIds", searchQuery.getFilmIds());
        }

        return sql.toString();
    }

    private String createOrderBy(ShowtimeConfigSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder();
        if (StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by s.").append(searchQuery.getSortBy().replace(".", " "));
        } else {
            sql.append("order by s.createdAt desc");
        }
        return sql.toString();

    }

}
