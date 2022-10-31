package com.it.doubledi.cinemamanager.domain.query;

import com.it.doubledi.cinemamanager._common.model.query.PagingQuery;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class LocationSearchQuery extends PagingQuery {
    private String keyword;
    private LocationStatus status;
}
