package com.it.doubledi.cinemamanager.domain.query;

import com.it.doubledi.cinemamanager._common.model.query.PagingQuery;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
public class ShowtimeConfigSearchQuery extends PagingQuery {
    private LocalDate time;
    private List<ShowtimeStatus> statuses;
    private List<String> roomIds;
    private List<String> filmIds;
}
