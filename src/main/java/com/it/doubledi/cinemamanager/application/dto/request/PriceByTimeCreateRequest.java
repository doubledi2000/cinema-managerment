package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceByTimeCreateRequest extends Request {
    private String id;

    @NotNull(message = "START_AT_REQUIRED")
    private Integer startAt;

    @NotNull(message = "END_AT_REQUIRED")
    private Integer endAt;

    private List<PriceCreateRequest> prices;
}
