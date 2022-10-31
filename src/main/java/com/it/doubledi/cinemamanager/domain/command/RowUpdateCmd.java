package com.it.doubledi.cinemamanager.domain.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RowUpdateCmd {
    private String id;
    private String roomId;
    private List<ChairUpdateCmd> chairs;
}
