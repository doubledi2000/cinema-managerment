package com.it.doubledi.cinemamanager.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateCmd {
    private String code;
    private String name;
    private String description;
    private Integer maxRow;
    private Integer maxChairPerRow;
    private String locationId;
}
