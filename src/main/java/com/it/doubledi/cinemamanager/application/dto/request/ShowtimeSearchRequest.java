package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowtimeSearchRequest extends PagingRequest {
    private String keyword;
}
