package com.it.doubledi.cinemamanager.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class DrinkCreateCmd {
    private String name;
    private Double price;
    private String locationId;
    private String description;
    private String fileId;
}
