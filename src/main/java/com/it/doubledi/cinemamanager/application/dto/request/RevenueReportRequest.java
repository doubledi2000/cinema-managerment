package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class RevenueReportRequest extends Request {
    private Instant startAt;
    private Instant endAt;
    private List<String> locationIds;
    private ReportType type;
}
