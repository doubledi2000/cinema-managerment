package com.it.doubledi.cinemamanager._common.persistence.support;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;

@Repository
public class SeqRepositoryImpl implements SeqRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public BigInteger nextValue(String seqName) {
        var createSeqOrExistQuery = entityManager.createNativeQuery(String.format("CREATE SEQUENCE IF NOT EXISTS %s", seqName));
        createSeqOrExistQuery.executeUpdate();
        Query query = entityManager.createNativeQuery(String.format("select nextval('%s')", seqName));
        var value = query.getSingleResult();
        return (BigInteger) value;
    }
}
