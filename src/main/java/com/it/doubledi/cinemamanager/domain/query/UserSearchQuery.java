package com.it.doubledi.cinemamanager.domain.query;

import com.it.doubledi.cinemamanager._common.model.query.PagingQuery;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
public class UserSearchQuery extends PagingQuery {
    private String keyword;
    private List<String> userIds;
    private List<String> locationIds;
}
