package com.it.doubledi.cinemamanager.domain.query;

import com.it.doubledi.cinemamanager._common.model.query.PagingQuery;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class FilmSearchQuery extends PagingQuery {
    private String keyword;
    private List<String> typeOfFilmIds;
    private List<String> producerIds;
    private List<String> filmIds;
}
