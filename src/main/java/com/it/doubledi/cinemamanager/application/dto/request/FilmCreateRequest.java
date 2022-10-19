package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilmCreateRequest extends Request {

    @NotBlank(message = "CODE_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.CODE_MAX_LENGTH, message = "CODE_LENGTH")
    private String code;

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, message = "NAME_LENGTH")
    private String name;

    @NotNull(message = "ALLOWED_AGE_FROM_REQUIRED")
    private Integer allowedAgeFrom;

    @NotNull(message = "STATUS_REQUIRED")
    private FilmStatus status;

    @Size(max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH, message = "DESC_LENGTH")
    private String description;

    private LocalDate releaseDate;

    private LocalDate ownershipDate;

    @NotNull(message = "DURATION_REQUIRED")
    private Integer duration;

    @NotBlank(message = "PRODUCER_ID_REQUIRED")
    @Size(max = ValidateConstraint.LENGTH.ID_MAX_LENGTH, message = "PRODUCER_ID_LENGTH")
    private String producerId;

    @NotEmpty(message = "FILM_TYPE_REQUIRED")
    private List<String> filmTypeIds;
}
