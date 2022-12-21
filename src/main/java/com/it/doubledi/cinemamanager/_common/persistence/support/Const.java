package com.it.doubledi.cinemamanager._common.persistence.support;

public enum Const {
    ROW_CODE("ROW", "ROW_CODE_SEQ" ),
    CHAIR_CODE("CHAIR", "CHAIR_CODE_SEQ" ),
    TICKET_CODE("TICKET", "TICKET_CODE_SEQ"),
    DRINK_CODE("DRINK", "DRINK_CODE_SEQ")
    ;
    private final String prefix;

    // must upper case
    private final String seqName;

    Const(String prefix, String seqName) {
        this.prefix = prefix;
        this.seqName = seqName.toUpperCase();
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSeqName() {
        return seqName;
    }
}
