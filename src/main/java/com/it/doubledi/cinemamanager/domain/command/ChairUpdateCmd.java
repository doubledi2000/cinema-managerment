package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager.infrastructure.support.enums.ChairType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChairUpdateCmd {
    private String id;
    private Integer serialOfChair;
    private ChairType chairType;
    private String rowId;
}
