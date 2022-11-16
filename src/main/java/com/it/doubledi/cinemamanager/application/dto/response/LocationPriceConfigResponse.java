package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.domain.PriceConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LocationPriceConfigResponse {
    private String locationId;
    private String locationCode;
    private String locationName;
    private List<PriceConfig> priceConfigs;
}
