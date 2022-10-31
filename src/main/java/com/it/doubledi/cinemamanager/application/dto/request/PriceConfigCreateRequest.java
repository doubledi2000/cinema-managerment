package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.PriceConfigType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceConfigCreateRequest extends Request {

    private String id;

    @NotNull(message = "PRICE_CONFIG_TYPE_REQUIRED")
    private PriceConfigType type;

    @NotNull(message = "DAY_OF_WEEK_REQUIRED")
    private int dayOfWeek;

    private String drinkId;

    private List<PriceByTimeCreateRequest> priceByTimes;
}
