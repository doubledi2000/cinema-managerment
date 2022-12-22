package com.it.doubledi.cinemamanager.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueReportByYearRequest {
    @NotBlank(message = "LOCATION_ID_REQUIRED")
    private String locationId;

    @NotNull(message = "DATE_REQUIRED")
    private Instant date;
}
