package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmScheduleCreateRequest {

    @NotNull(message = "START_AT_REQUIRED")
    @Min(value = Constant.MIN_START_AT)
    @Max(value = Constant.MAX_START_AT)
    private Integer startAt;

    @NotBlank(message = "FILM_ID_REQUIRED")
    private String filmId;

    private Boolean autoGenerateTicket;

}
