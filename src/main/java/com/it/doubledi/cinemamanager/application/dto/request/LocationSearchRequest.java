package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.LocationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSearchRequest extends PagingRequest {
    private String keyword;
    private LocationStatus status;
}
