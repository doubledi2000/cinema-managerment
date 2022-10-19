package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.application.dto.request.PriceByTimeCreateRequest;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigType;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
@Builder
public class ConfigPriceCreateCmd {
    private String id;
    private PriceConfigType type;
    private DayOfWeek dayOfWeek;
    private String drinkId;
    private List<PriceByTimeCreateCmd> priceByTimeCreateRequests;
}
