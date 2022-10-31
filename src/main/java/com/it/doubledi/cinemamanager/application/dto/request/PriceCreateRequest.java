package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCreateRequest {
    private String id;
    private Float price;
    private ChairType chairType;
}
