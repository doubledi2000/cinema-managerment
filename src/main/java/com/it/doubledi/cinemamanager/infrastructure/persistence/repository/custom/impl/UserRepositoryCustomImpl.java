package com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.impl;

import com.it.doubledi.cinemamanager.domain.query.UserSearchQuery;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.TypeOfFilmEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.custom.UserRepositoryCustom;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> search(UserSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select u from UserEntity u ");
        sql.append(createWhereQuery(searchQuery, value));
        sql.append(createOrderBy(searchQuery));
        Query query = entityManager.createQuery(sql.toString(), UserEntity.class);
        value.forEach(query::setParameter);
        query.setFirstResult((searchQuery.getPageIndex() - 1) * searchQuery.getPageSize());
        query.setMaxResults(searchQuery.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long count(UserSearchQuery searchQuery) {
        Map<String, Object> value = new HashMap<>();
        StringBuilder sql = new StringBuilder("Select u from UserEntity u ");
        sql.append(createWhereQuery(searchQuery, value));
        Query query = entityManager.createQuery(sql.toString(), Long.class);
        value.forEach(query::setParameter);
        return (long)query.getSingleResult();
    }

    private String createWhereQuery(UserSearchQuery searchQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder(" WHERE u.deleted = false ");
        if (StringUtils.hasText(searchQuery.getKeyword())) {
            sql.append(" AND (u.fullName like :keyword or u.username like :keyword or u.employeeCode like :keyword u.phoneNumber like :keyword ");
            values.put("keyword", searchQuery.getKeyword());
        }
        if (CollectionUtils.isEmpty(searchQuery.getUserIds())) {
            sql.append(" AND u.id in :ids");
            values.put("ids", searchQuery.getUserIds());
        }
        return sql.toString();
    }

    private String createOrderBy(UserSearchQuery searchQuery) {
        StringBuilder sql = new StringBuilder();
        if(StringUtils.hasLength(searchQuery.getSortBy())) {
            sql.append("order by u.").append(searchQuery.getSortBy().replace(".", " "));
        }else {
            sql.append("order by u.createdAt desc");
        }
        return sql.toString();
    }
}
