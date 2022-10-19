package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RoomCreateRequest extends Request {

    @NotBlank(message = "CODE_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.CODE_MAX_LENGTH, message = "CODE_LENGTH")
    private String code;

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, message = "NAME_LENGTH")
    private String name;

    @Size(max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH, message = "DESC_LENGTH")
    private String description;

    @Min(value = 1, message = "MIN_VALUE")
    private Integer maxRow;

    @Min(value = 1, message = "MIN_VALUE")
    private Integer maxChairPerRow;

    @NotBlank(message = "LOCATION_ID_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.ID_MAX_LENGTH, message = "ID_LENGTH")
    private String locationId;

    private Boolean defaultSetting;

}
