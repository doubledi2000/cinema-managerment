package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class ShowtimeCreateRequest extends Request {
    @NotBlank(message = "FILM_ID_REQUIRED")
    private String filmId;

    @NotBlank(message = "ROOM_ID_REQUIRED")
    private String roomId;

    private LocalDate premiereDate;

    @NotNull(message = "START_AT_REQUIRED")
    @Min(value = Constant.MIN_START_AT)
    @Max(value = Constant.MAX_START_AT)
    private Integer startAt;

    private Boolean autoGenerateTicket;
}
