package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PriceByTimeCreateCmd {
    private String id;
    private Integer startAt;
    private Integer endAt;
    private List<PriceCreateCmd> prices;
}
