package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.constant.Constant;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeCreateRequest extends Request {

    @NotBlank(message = "ROOM_ID_REQUIRED")
    private String roomId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate premierDate;
    @NotEmpty(message = "FILM_REQUIRED")
    List<@Valid FilmScheduleCreateRequest> films;


}
