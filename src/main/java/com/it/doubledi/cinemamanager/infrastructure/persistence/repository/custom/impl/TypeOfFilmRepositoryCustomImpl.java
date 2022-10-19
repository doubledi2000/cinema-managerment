package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.domain.query.TypeOfFilmSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.TypeOfFilmRepositoryCustom;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TypeOfFilmRepositoryCustomImpl implements TypeOfFilmRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<TypeOfFilmEntity> search(TypeOfFilmSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select tof from TypeOfFilmEntity tof");
        sql.append(createWhereQuery(searchQuery, value));
        sql.append(createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), TypeOfFilmEntity.class);
        value.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(TypeOfFilmSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select count(*) from TypeOfFilmEntity tof ");
        sql.append(createWhereQuery(searchQuery, value));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        value.forEach(query::setParameter);
        return (Long) query.getSingleResult();

    }

    private String createWhereQuery(TypeOfFilmSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE tof.deleted = false ");
        if (StringUtils.hasLength(searchQuery.getKeyword())) {
            sql.append(" AND (tof.name like :keyword or tof.code like :keyword) ");
            values.put("keyword", SqlUtils.encodeKeyword(searchQuery.getKeyword()));
        }

        if(Objects.nonNull(searchQuery.getStatus())) {
            sql.append(" AND (tof.status = :status)");
            values.put("status", searchQuery.getStatus());
        }

        return sql.toString();
    }

    private String createOrderBy(TypeOfFilmSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder();
        if(StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by tof.").append(searchQuery.getSortBy().replace(".", " "));
        }else {
            sql.append("order by tof.createdAt desc");
        }
        return sql.toString();
    }
}
