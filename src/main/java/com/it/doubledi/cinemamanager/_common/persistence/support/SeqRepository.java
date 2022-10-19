package com.it.doubledi.cinemamanager._common.persistence.support;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

public interface SeqRepository {
    BigInteger nextValue(String seqName);

    @Transactional
    default String nextValue(String prefix, String seqName) {
        return prefix + nextValue(seqName);
    }

    @Transactional
    default String generateRowCode() {
        return nextValue(Const.ROW_CODE.getPrefix() + "-", Const.ROW_CODE.getSeqName());
    }

    @Transactional
    default String generateChairCode() {
        return nextValue(Const.CHAIR_CODE.getPrefix() + "-", Const.CHAIR_CODE.getSeqName());
    }

}
