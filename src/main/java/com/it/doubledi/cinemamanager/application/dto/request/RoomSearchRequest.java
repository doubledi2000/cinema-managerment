package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomSearchRequest extends PagingRequest {
    private String keyword;
}
