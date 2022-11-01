package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.domain.query.FilmSearchQuery;
import com.it.doubledi.cinemamanager.domain.query.ProducerSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.FilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.ProducerEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.ProducerRepositoryCustom;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerRepositoryCustomImpl implements ProducerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProducerEntity> search(ProducerSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select p from ProducerEntity p ");
        sql.append(createWhereQuery(searchQuery, value));
        sql.append(createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), ProducerEntity.class);
        value.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(ProducerSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select count(*) from ProducerEntity p ");
        sql.append(createWhereQuery(searchQuery, value));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        value.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(ProducerSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE p.deleted = false ");
        if (StringUtils.hasLength(searchQuery.getKeyword())) {
            sql.append(" AND (p.name like :keyword or p.code like :keyword) ");
            values.put("keyword", SqlUtils.encodeKeyword(searchQuery.getKeyword()));
        }
        return sql.toString();
    }

    private String createOrderBy(ProducerSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder();
        if (StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by p.").append(searchQuery.getSortBy().replace(".", " "));
        } else {
            sql.append("order by p.createdAt desc");
        }
        return sql.toString();
    }
}
