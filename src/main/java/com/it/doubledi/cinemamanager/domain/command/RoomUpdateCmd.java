package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomUpdateCmd {
    private String code;
    private String name;
    private String description;
    private List<RowUpdateCmd> rows;
}
