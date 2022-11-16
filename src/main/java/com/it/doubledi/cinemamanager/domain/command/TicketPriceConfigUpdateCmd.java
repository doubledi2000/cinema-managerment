package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.domain.PriceConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TicketPriceConfigUpdateCmd {
    private String id;
    private List<PriceConfig> configPrices;
}
