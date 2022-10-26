package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LocationPriceConfigCmd {
    private String locationId;
    private List<PriceConfigCreateCmd> priceConfigs;
}
