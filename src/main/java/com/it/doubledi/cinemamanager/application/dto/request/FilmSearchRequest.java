package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmSearchRequest extends PagingRequest {
    private String keyword;
    private List<String> locationIds;
    private List<String> producerIds;
    private List<String> filmIds;
}
