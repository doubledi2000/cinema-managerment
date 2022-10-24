package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class TypeOfFilmCreateRequest extends Request {
    @NotBlank(message = "CODE_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.CODE_MAX_LENGTH)
    private String code;

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Size(max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

}
