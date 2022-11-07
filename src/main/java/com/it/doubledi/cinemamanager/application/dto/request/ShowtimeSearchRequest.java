package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeSearchRequest extends PagingRequest {
    private String keyword;
    private Integer startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate premierDate;
    private List<String> filmIds;
    private List<String> typeOfFilmIds;
    private List<String> roomIds;
}
