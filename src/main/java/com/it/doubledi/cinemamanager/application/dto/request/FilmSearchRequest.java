package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import lombok.Data;

@Data
public class FilmSearchRequest extends PagingRequest {
    private String keyword;
}
