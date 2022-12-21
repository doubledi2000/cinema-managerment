package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkCreateRequest extends Request {
    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    @Min(value = 0, message = "DRINK_PRICE_MIN_VALUE")
    private Double price;

    @NotBlank(message = "LOCATION_ID_REQUIRED")
    private String locationId;

    private String description;

    private String fileId;
}
