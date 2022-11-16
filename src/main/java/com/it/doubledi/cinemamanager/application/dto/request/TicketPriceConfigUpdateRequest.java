package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager.domain.PriceConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketPriceConfigUpdateRequest {
    @NotBlank(message = "LOCATION_ID_REQUIRED")
    private String id; //location id

    @NotEmpty(message = "PRICE_CONFIG_NOT_EMPTY")
    List<PriceConfig> configPrices;
}
