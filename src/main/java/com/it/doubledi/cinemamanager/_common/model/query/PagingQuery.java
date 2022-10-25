package com.it.doubledi.cinemamanager._common.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class PagingQuery extends Query {
    public static final String ASC_SYMBOL = "asc";
    public static final String DESC_SYMBOL = "desc";

    protected int pageIndex = 1;

    protected int pageSize = 30;

    protected String sortBy;

    private String keyword;
}
