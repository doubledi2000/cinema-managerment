package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.domain.query.LocationSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.LocationEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.LocationRepositoryCustom;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LocationEntity> search(LocationSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select l from LocationEntity l");
        sql.append(createWhereQuery(searchQuery, value));
        sql.append(createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), LocationEntity.class);
        value.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(LocationSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select count(*) from LocationEntity l ");
        sql.append(createWhereQuery(searchQuery, value));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        value.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(LocationSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE l.deleted = false ");
        if (StringUtils.hasLength(searchQuery.getKeyword())) {
            sql.append(" AND (l.name like :keyword or l.code like :keyword) ");
            values.put("keyword", SqlUtils.encodeKeyword(searchQuery.getKeyword()));
        }

        if (!CollectionUtils.isEmpty(searchQuery.getIds())) {
            sql.append(" AND l.id in :ids ");
            values.put("ids", searchQuery.getIds());
        }

        if (Objects.nonNull(searchQuery.getStatus())) {
            sql.append(" AND (l.status = :status)");
            values.put("status", searchQuery.getStatus());
        }

        return sql.toString();
    }

    private String createOrderBy(LocationSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder();
        if (StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by l.").append(searchQuery.getSortBy().replace(".", " "));
        } else {
            sql.append("order by l.createdAt desc");
        }
        return sql.toString();
    }
}
