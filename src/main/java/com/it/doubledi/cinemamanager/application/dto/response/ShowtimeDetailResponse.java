package com.it.doubledi.cinemamanager.application.dto.response;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ShowtimeStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowtimeDetailResponse {
    private String id;
    private Integer startAt;
    private Integer endAt;
    private String roomId;
    private ShowtimeStatus status;
}
