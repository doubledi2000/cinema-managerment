package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreateCmd {
    private String itemId;
    private Integer quantity;
}
