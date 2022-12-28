package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.PagingRequest;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.DrinkStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkSearchRequest extends PagingRequest {
    private String keyword;
    private List<String> locationIds;
    private DrinkStatus status;
}
