package com.it.doubledi.cinemamanager.domain.query;

import com.it.doubledi.cinemamanager._common.model.query.PagingQuery;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.TypeOfFilmStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class TypeOfFilmSearchQuery extends PagingQuery {
    private String keyword;
    private TypeOfFilmStatus status;
}
