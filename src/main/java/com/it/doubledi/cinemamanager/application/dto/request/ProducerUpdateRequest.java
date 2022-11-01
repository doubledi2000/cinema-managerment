package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerUpdateRequest extends Request {

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String name;

    @Size(max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @NotBlank(message = "REPRESENTATIVE_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String representative;

    @NotBlank(message = "NATIONALLY_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String nationally;
}
