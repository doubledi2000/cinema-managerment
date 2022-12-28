package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class RevenueReportRequest extends Request {
//    @NotNull(message = "START_AT_REQUIRED")
    private Instant startAt;

//    @NotNull(message = "END_AT_REQUIRED")
    private Instant endAt;
    private List<String> locationIds;
    private ReportType type;
}
