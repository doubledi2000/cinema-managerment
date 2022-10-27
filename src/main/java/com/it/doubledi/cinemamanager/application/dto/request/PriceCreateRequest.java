package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceCreateRequest {
    private String id;
    private Float price;
    private ChairType chairType;
}
