package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomUpdateRequest {

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, message = "NAME_LENGTH")
    private String name;

    @Size(max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH, message = "DESC_LENGTH")
    private String description;

    @Min(value = 1, message = "MIN_VALUE")
    private Integer maxRow;

    @Min(value = 1, message = "MIN_VALUE")
    private Integer maxChairPerRow;
}
