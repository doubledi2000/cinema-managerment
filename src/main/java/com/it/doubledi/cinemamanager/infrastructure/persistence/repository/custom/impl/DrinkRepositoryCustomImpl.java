package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.domain.query.DrinkSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.DrinkEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.DrinkRepositoryCustom;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DrinkRepositoryCustomImpl implements DrinkRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DrinkEntity> search(DrinkSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder(" Select d from DrinkEntity d ");
        Map<String, Object> values = new HashMap<>();
        sql.append(this.createWhereQuery(searchQuery, values));
        sql.append(this.createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), DrinkEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(DrinkSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder(" Select count(*) from DrinkEntity d ");
        Map<String, Object> values = new HashMap<>();
        sql.append(this.createWhereQuery(searchQuery, values));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        values.forEach(query::setParameter);

        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(DrinkSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" where d.deleted = false ");
        if (StringUtils.hasLength(searchQuery.getKeyword())) {
            sql.append(" and d.name like :keyword or d.code like :keyword");
            values.put("keyword", SqlUtils.encodeKeyword(searchQuery.getKeyword()));
        }

        if (!CollectionUtils.isEmpty(searchQuery.getLocationIds())) {
            sql.append(" and d.locationId in :locationId");
            values.put("locationId", searchQuery.getLocationIds());
        }

        if (Objects.nonNull(searchQuery.getStatus())) {
            sql.append(" and d.status = :status ");
            values.put("status", searchQuery.getStatus());
        }

        return sql.toString();
    }

    private String createOrderBy(DrinkSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by d.").append(searchQuery.getSortBy().replace(".", " "));
        } else {
            sql.append(" order by d.createdAt desc");
        }
        return sql.toString();
    }

}

