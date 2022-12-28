package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager._common.model.validator.ValidateConstraint;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.FilmStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Size(max = ValidateConstraint.LENGTH.DESC_MAX_LENGTH, message = "DESC_LENGTH")
    private String actors;

    @Size(max = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, message = "NAME_LENGTH")
    private String directors;

    @NotEmpty(message = "PRODUCER_ID_REQUIRED")
    private List<String> producerIds;

    @NotEmpty(message = "FILE_REQUIRED")
    private String fileId;

    @NotEmpty(message = "FILM_TYPE_REQUIRED")
    private List<String> filmTypeIds;
}
