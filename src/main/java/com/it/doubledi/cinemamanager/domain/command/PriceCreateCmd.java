package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceCreateCmd {
    private String id;
    private Float price;
    private ChairType chairType;
}
