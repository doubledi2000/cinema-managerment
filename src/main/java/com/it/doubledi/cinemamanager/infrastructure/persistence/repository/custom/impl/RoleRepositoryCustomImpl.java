package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager._common.persistence.support.SqlUtils;
import com.it.doubledi.cinemamanager.domain.query.RoleSearchQuery;
import com.it.doubledi.cinemamanager.domain.query.RoomSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoleEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.RoomEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.RoleRepositoryCustom;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleEntity> search(RoleSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select r from RoleEntity r");
        sql.append(createWhereQuery(searchQuery, value));
        sql.append(createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), RoleEntity.class);
        value.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(RoleSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select count(*) from RoleEntity r ");
        sql.append(createWhereQuery(searchQuery, value));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        value.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
    private String createWhereQuery(RoleSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE r.deleted = false ");
        if (StringUtils.hasLength(searchQuery.getKeyword())) {
            sql.append(" AND (r.name like :keyword or r.code like :keyword) ");
            values.put("keyword", SqlUtils.encodeKeyword(searchQuery.getKeyword()));
        }

        return sql.toString();
    }

    private String createOrderBy(RoleSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder();
        if(StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by r.").append(searchQuery.getSortBy().replace(".", " "));
        }else {
            sql.append("order by r.createdAt desc");
        }
        return sql.toString();
    }
}
